package com.app.pattern.templatemethod;

import com.app.dto.request.ApiBookingRequestDTO;
import com.app.enums.BookingSource;
import com.app.pojo.Paymentmethod;
import com.app.pojo.Room;
import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingdetail;
import com.app.pojo.Roombookingservice;
import com.app.pojo.Roombookingstatus;
import com.app.pojo.Service;
import com.app.pojo.User;
import com.app.pojo.Voucher;
import com.app.repositories.PaymentMethodRepository;
import com.app.repositories.RoomBookingDetailRepository;
import com.app.repositories.RoomBookingRepository;
import com.app.repositories.RoomBookingServiceRepository;
import com.app.repositories.RoomBookingStatusRepository;
import com.app.repositories.RoomRepository;
import com.app.repositories.ServiceRepository;
import com.app.repositories.UserRepository;
import com.app.repositories.VoucherRepository;
import com.app.services.RoomBookingService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ApiBookingServiceImpl extends AbstractBookingProcess implements RoomBookingService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    RoomBookingDetailRepository roomBookingDetailRepository;

    @Autowired
    RoomBookingStatusRepository roomBookingStatusRepository;

    @Autowired
    RoomBookingServiceRepository roomBookingServiceRepository;

    @Override
    protected Room getRoomById(Integer roomId) {
        return this.roomRepository.getBasicRoomById(roomId);
    }

    private BigDecimal calculateRoomPrice(BigDecimal basePrice, Date checkIn, Date checkOut) {
        BigDecimal p1Day = basePrice;
        BigDecimal pOvernight = basePrice.multiply(new BigDecimal("0.7"));
        BigDecimal p2Hours = basePrice.multiply(new BigDecimal("0.3"));
        BigDecimal pNextHour = basePrice.multiply(new BigDecimal("0.1"));

        long diffMillis = checkOut.getTime() - checkIn.getTime();
        long diffMinutes = diffMillis / 60000;
        long totalHours = diffMinutes / 60;
        if (diffMinutes % 60 > 15) {
            totalHours++;
        }

        int nights = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkIn);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (cal.getTime().before(checkIn)) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        while (cal.getTime().before(checkOut) || cal.getTime().equals(checkOut)) {
            nights++;
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (nights == 0) {
            long extra = Math.max(0, totalHours - 2);
            BigDecimal hourlyTotal = p2Hours.add(pNextHour.multiply(new BigDecimal(extra)));
            return p1Day.min(pOvernight).min(hourlyTotal);
        } else {
            long extraHours = Math.max(0, totalHours - (nights * 24));
            BigDecimal extraCost = pNextHour.multiply(new BigDecimal(extraHours)).min(p1Day);
            return p1Day.multiply(new BigDecimal(nights)).add(extraCost);
        }
    }

    private BigDecimal[] calculateServicesPriceAfterVoucher(ApiBookingRequestDTO request, BigDecimal expectedTotal) {
        if (request.getServices() != null && !request.getServices().isEmpty()) {
            for (ApiBookingRequestDTO.ServiceOrderDTO s : request.getServices()) {
                Service serviceDb = this.serviceRepository.getServiceById(s.getServiceId());
                if (serviceDb == null || (serviceDb.getIsDeleted() != null && serviceDb.getIsDeleted() == 1)) {
                    throw new IllegalArgumentException("Dịch vụ đính kèm không tồn tại hoặc đã ngừng kinh doanh.");
                }
                if (serviceDb.getStock() < s.getQuantity()) {
                    throw new IllegalArgumentException("Dịch vụ '" + serviceDb.getName() + "' chỉ còn "
                            + serviceDb.getStock() + " sản phẩm.");
                }
                BigDecimal sPrice = serviceDb.getPrice();
                BigDecimal sQty = new BigDecimal(s.getQuantity());
                expectedTotal = expectedTotal.add(sPrice.multiply(sQty));
            }
        }

        BigDecimal discountMoney = BigDecimal.ZERO;

        if (request.getVoucherId() != null) {
            Voucher voucher = this.voucherRepository.getVoucherById(request.getVoucherId());
            if (voucher != null) {
                if (voucher.getMinRequire() != null && expectedTotal.compareTo(voucher.getMinRequire()) < 0) {
                    throw new IllegalArgumentException("Tổng hóa đơn chưa đạt mức tối thiểu để áp Voucher này.");
                }
                if (voucher.getPercentDiscount() != null && voucher.getPercentDiscount().compareTo(BigDecimal.ZERO) > 0) {
                    discountMoney = expectedTotal.multiply(voucher.getPercentDiscount())
                            .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

                    if (voucher.getMaxDiscount() != null && discountMoney.compareTo(voucher.getMaxDiscount()) > 0) {
                        discountMoney = voucher.getMaxDiscount();
                    }
                } else if (voucher.getMaxDiscount() != null) {
                    discountMoney = voucher.getMaxDiscount();
                } else {
                    throw new IllegalArgumentException("Voucher này bị lỗi không có phần trăm giảm giá.");
                }
                expectedTotal = expectedTotal.subtract(discountMoney).max(BigDecimal.ZERO);
            }
        }
        
        return new BigDecimal[]{expectedTotal, discountMoney};
    }

    @Override
    protected BookingPriceContext validateData(Room roomDb, ApiBookingRequestDTO request) {
        if (roomDb.getIsDeleted() != null && roomDb.getIsDeleted() == 1) {
            throw new IllegalArgumentException("Phòng này đã bị ngừng hoạt động hoặc bị xóa.");
        }
        if (request.getClientCapacity() > roomDb.getCapacity()) {
            throw new IllegalArgumentException("Số lượng khách (" + request.getClientCapacity()
                    + ") vượt quá sức chứa tối đa của phòng (" + roomDb.getCapacity() + " người).");
        }

        BigDecimal roomPrice = calculateRoomPrice(roomDb.getPrice(), request.getCheckIn(), request.getCheckOut());

        BigDecimal[] serviceCalcResult = calculateServicesPriceAfterVoucher(request, roomPrice);
        BigDecimal expectedTotal = serviceCalcResult[0];
        BigDecimal discountMoney = serviceCalcResult[1];

        if (expectedTotal.compareTo(request.getClientPrice()) != 0) {
            throw new IllegalArgumentException("Lỗi đồng bộ giá! Server tính: " + expectedTotal
                    + ", Client gửi: " + request.getClientPrice() + ". Dữ liệu có thể đã bị can thiệp.");
        }

        Integer paymentMethodId = request.getPaymentMethodId();
        Paymentmethod cashMethod = this.paymentMethodRepository.getPaymentMethodByName("Cash");

        if (cashMethod == null) {
            throw new IllegalStateException("Thiếu phương thức thanh toán bằng tiền mặt.");
        }

        boolean isOnline = request.getBookingSource() == BookingSource.ONLINE;
        boolean isCash = paymentMethodId.equals(cashMethod.getPaymentMethodId());

        if (isOnline && isCash) {
            throw new IllegalArgumentException("Khách đặt trực tuyến không được phép chọn tiền mặt để đặt cọc.");
        }

        return new BookingPriceContext(roomPrice, discountMoney, expectedTotal);
    }

    @Override
    protected void checkRoomAvailability(ApiBookingRequestDTO request) {
        boolean isBusy = this.roomRepository.isRoomBusy(request.getRoomId(), request.getCheckIn(), request.getCheckOut());
        if (isBusy) {
            throw new IllegalArgumentException("Phòng này đã được đặt vào thời điểm này.");
        }
    }

    @Override
    protected void lockRoom(Room room) {
        this.roomRepository.lockRoom(room);
    }

    @Override
    protected Roombooking saveToDb(Room room, ApiBookingRequestDTO request, BookingPriceContext priceContext) {
        User u = this.userRepository.getUserById(request.getUserBookingId());
        Roombookingstatus rbs = this.roomBookingStatusRepository.getRoomBookingStatusByName("Pending");
        Paymentmethod payment = this.paymentMethodRepository.getPaymentMethodById(request.getPaymentMethodId());

        if (u == null) {
            throw new IllegalArgumentException("Không có user đặt phòng");
        }
        if (rbs == null) {
            throw new IllegalArgumentException("Hệ thống không có roombookingstatus Pending");
        }
        
        if (payment == null) {
            throw new IllegalArgumentException("Hệ thống không có payment method");
        }

        Voucher voucher = null;
        if (request.getVoucherId() != null) {
            voucher = this.voucherRepository.getVoucherById(request.getVoucherId());
            voucher.setQuantity(voucher.getQuantity() - 1);
        }

        long twoHoursInMillis = 2 * 60 * 60 * 1000;
        Date expiredTime = new Date(request.getCheckIn().getTime() + twoHoursInMillis);

        Roombooking rb = Roombooking.builder()
                .userId(u)
                .userName(u.getName())
                .bookingCheckIn(request.getCheckIn())
                .bookingCheckOut(request.getCheckOut())
                .expiredTime(expiredTime)
                .voucherId(voucher)
                .voucherDiscountMoney(priceContext.getDiscountMoney())
                .totalAmount(priceContext.getTotalAmount())
                .bookingSource(request.getBookingSource().toString())
                .roomBookingStatusId(rbs)
                .paymentMethodId(payment)
                .build();

        this.roomBookingRepository.saveRoomBooking(rb);

        Roombookingdetail detail = Roombookingdetail.builder()
                .roomBookingId(rb)
                .roomId(room)
                .timeIn(request.getCheckIn())
                .timeOut(request.getCheckOut())
                .roomName(room.getRoomName())
                .price(priceContext.getRoomPrice())
                .build();
        this.roomBookingDetailRepository.saveRoomBookingDetail(detail);

        if (request.getServices() != null && !request.getServices().isEmpty()) {
            for (ApiBookingRequestDTO.ServiceOrderDTO s : request.getServices()) {
                Service serviceDb = this.serviceRepository.getServiceById(s.getServiceId());

                int rowsUpdated = this.serviceRepository.deductStock(serviceDb.getServiceId(), s.getQuantity());

                if (rowsUpdated == 0) {
                    throw new IllegalStateException("Giao dịch thất bại. Dịch vụ '" + serviceDb.getName() + "' vừa mới hết hàng.");
                }

                Roombookingservice rbService = Roombookingservice.builder()
                        .roomBookingDetailId(detail)
                        .serviceId(serviceDb)
                        .quantity(s.getQuantity())
                        .unitServicePrice(serviceDb.getPrice())
                        .createAt(new Date())
                        .serviceName(serviceDb.getName())
                        .build();
                this.roomBookingServiceRepository.saveRoomBookingService(rbService);
            }
        }

        return rb;
    }

    @Override
    protected String executePayment(Roombooking booking, Integer paymentMethodId) {
        return "";
    }
}