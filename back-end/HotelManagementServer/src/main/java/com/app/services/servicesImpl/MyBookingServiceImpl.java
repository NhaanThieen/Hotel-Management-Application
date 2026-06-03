package com.app.services.servicesImpl;

import com.app.dto.response.ReceiptResponseDTO;
import com.app.dto.response.mybooking.MyBookingHistoryRawResponse;
import com.app.dto.response.mybooking.MyBookingHistoryResponse;
import com.app.dto.response.mybooking.MyBookingHistoryDetailResponse;
import com.app.pojo.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.app.repositories.MyBookingRepository;
import com.app.repositories.RoomBookingRepository;
import com.app.services.MyBookingService;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class MyBookingServiceImpl implements MyBookingService {

    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private MyBookingRepository bookingRepository;
    
    @Autowired
    private com.app.repositories.FeedbackRepository feedbackRepository;
    
    
    @Autowired
    private RoomBookingRepository roomBookingRepository;
    

    private String formatDate(java.util.Date date, DateTimeFormatter formatter) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
    }

    @Override
    public List<MyBookingHistoryResponse> getBookingHistory(String userName) {
        List<Roombooking> bookings = bookingRepository.getBookingsByUserName(userName);
        List<MyBookingHistoryResponse> responses = new ArrayList<>();
        for (Roombooking booking : bookings) { responses.add(mapToReactDTO(booking)); }
        return responses;
    }

    @Override
    @Transactional 
    public void cancelBooking(Integer bookingId, String userName) {
        Roombooking booking = bookingRepository.getBookingByIdAndUserName(bookingId, userName);
        if (booking == null) throw new NoSuchElementException("Không tìm thấy đơn đặt phòng.");

        String currentStatus = booking.getRoomBookingStatusId() != null ? booking.getRoomBookingStatusId().getName() : "";
        if (!"Pending".equalsIgnoreCase(currentStatus)) throw new IllegalStateException("Chỉ có thể hủy đơn đang ở trạng thái Chờ thanh toán.");

        Roombookingstatus cancelStatus = bookingRepository.getBookingStatusByName("Cancel");
        booking.setRoomBookingStatusId(cancelStatus);

        if (booking.getRoombookingdetailList() != null) {
            for (Roombookingdetail detail : booking.getRoombookingdetailList()) {
                Room room = detail.getRoomId();
                if (room != null) room.setRoomStatusId(new com.app.pojo.Roomstatus(1)); 
            }
        }
    }

    private MyBookingHistoryResponse mapToReactDTO(Roombooking booking) {
        MyBookingHistoryResponse dto = new MyBookingHistoryResponse();
        dto.setId(booking.getRoomBookingId());
        dto.setType("ROOM");
        dto.setTitle(String.format(Locale.forLanguageTag("vi-VN"), "Mã hóa đơn: 10000#%d", booking.getRoomBookingId()));

        String statusName = booking.getRoomBookingStatusId() != null ? booking.getRoomBookingStatusId().getName() : "Pending";
        switch (statusName.toLowerCase()) {
            case "pending": dto.setBadgeText("Chờ thanh toán"); dto.setBadgeBg("warning"); break;
            case "cancel": dto.setBadgeText("Đã hủy"); dto.setBadgeBg("secondary"); break;
            case "paid": dto.setBadgeText("Đã thanh toán"); dto.setBadgeBg("success"); break;
            default: dto.setBadgeText(statusName); dto.setBadgeBg("info");
        }

        dto.setSubText("Hình thức: " + (booking.getBookingSource() != null ? booking.getBookingSource() : "Website trực tuyến"));
        dto.setAmount(calculateTotalAmount(booking));
        
        MyBookingHistoryRawResponse raw = new MyBookingHistoryRawResponse();
        raw.setDayStart(formatDate(booking.getBookingCheckIn(), DAY_FORMATTER));
        raw.setDayEnd(formatDate(booking.getBookingCheckOut(), DAY_FORMATTER));
        raw.setTimeStart(formatDate(booking.getBookingCheckIn(), TIME_FORMATTER));
        raw.setTimeEnd(formatDate(booking.getBookingCheckOut(), TIME_FORMATTER));
        raw.setVoucherDiscountMoney(booking.getVoucherDiscountMoney() != null ? booking.getVoucherDiscountMoney() : BigDecimal.ZERO);
        raw.setDepositAmount(booking.getDepositAmount() != null ? booking.getDepositAmount() : BigDecimal.ZERO);

        List<MyBookingHistoryDetailResponse> detailList = new ArrayList<>();
        if (booking.getRoombookingdetailList() != null) {
            for (Roombookingdetail d : booking.getRoombookingdetailList()) {
                MyBookingHistoryDetailResponse dRes = new MyBookingHistoryDetailResponse();
                dRes.setRoomBookingDetailId(d.getRoomBookingDetailId());
                dRes.setRoomName(d.getRoomName());
                dRes.setPrice(d.getPrice() != null ? d.getPrice() : BigDecimal.ZERO);
                detailList.add(dRes);
            }
        }
        raw.setDetails(detailList);
        dto.setRaw(raw);
        dto.setIsReviewed(feedbackRepository.isFeedbackExist(booking.getRoomBookingId()));
        return dto;
    }

    private BigDecimal calculateTotalAmount(Roombooking booking) {
        if (booking.getRoombookingdetailList() == null || booking.getRoombookingdetailList().isEmpty()) return BigDecimal.ZERO;
        LocalDate startDate = Instant.ofEpochMilli(booking.getBookingCheckIn().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(booking.getBookingCheckOut().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        if (nights <= 0) nights = 1;

        BigDecimal roomAmount = BigDecimal.ZERO;
        for (Roombookingdetail detail : booking.getRoombookingdetailList()) {
            BigDecimal price = detail.getPrice() != null ? detail.getPrice() : BigDecimal.ZERO;
            roomAmount = roomAmount.add(price.multiply(BigDecimal.valueOf(nights)));
        }
        BigDecimal vat = roomAmount.multiply(BigDecimal.valueOf(0.10));
        BigDecimal voucher = booking.getVoucherDiscountMoney() != null ? booking.getVoucherDiscountMoney() : BigDecimal.ZERO;
        BigDecimal total = roomAmount.add(vat).subtract(voucher);
        return total.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public ReceiptResponseDTO getReceiptDetail(Integer bookingId) {
        Roombooking booking = roomBookingRepository.getRoomBookingById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn");
        }

        User user = booking.getUserId();
        
        Roombookingdetail mainDetail = null;
        String roomTypeName = "N/A";
        if (booking.getRoombookingdetailList() != null && !booking.getRoombookingdetailList().isEmpty()) {
            mainDetail = booking.getRoombookingdetailList().get(0);
            if (mainDetail.getRoomId() != null && mainDetail.getRoomId().getRoomTypeId() != null) {
                roomTypeName = mainDetail.getRoomId().getRoomTypeId().getName();
            }
        }

        List<ReceiptResponseDTO.ServiceItemDTO> servicesUsed = new java.util.ArrayList<>();
        BigDecimal totalServicesAmount = BigDecimal.ZERO;

        if (mainDetail != null && mainDetail.getRoombookingserviceList() != null) {
            for (Roombookingservice srv : mainDetail.getRoombookingserviceList()) {
                servicesUsed.add(ReceiptResponseDTO.ServiceItemDTO.builder()
                        .serviceName(srv.getServiceName())
                        .quantity(srv.getQuantity())
                        .unitPriceAtUse(srv.getUnitServicePrice())
                        .build());
                totalServicesAmount = totalServicesAmount.add(
                        srv.getUnitServicePrice().multiply(new BigDecimal(srv.getQuantity()))
                );
            }
        }

        long diffTime = booking.getBookingCheckOut().getTime() - booking.getBookingCheckIn().getTime();
        int totalNights = (int) Math.ceil((double) diffTime / (1000 * 60 * 60 * 24));
        if (totalNights <= 0) totalNights = 1;

        BigDecimal roomPricePerNight = mainDetail != null ? mainDetail.getPrice() : BigDecimal.ZERO;
        BigDecimal totalRoomAmount = roomPricePerNight.multiply(new BigDecimal(totalNights));

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm - yyyy-MM-dd");

        return ReceiptResponseDTO.builder()
                .receiptId(booking.getRoomBookingId())
                .userName(user != null ? user.getName() : "Khách ẩn danh")
                .userPhone(user != null ? user.getPhone() : "Không xác định")
                .userPaidId(user != null ? String.valueOf(user.getUserId()) : "N/A")
                .timeCheckIn(sdf.format(booking.getBookingCheckIn()))
                .timeCheckOut(sdf.format(booking.getBookingCheckOut()))
                .staffId(booking.getStaffId() != null ? String.valueOf(booking.getStaffId().getUserId()) : "Hệ Thống")
                .staffName(booking.getStaffId() != null ? booking.getStaffId().getName() : "Auto-Booking")
                .vipDiscountAmount(booking.getVoucherDiscountMoney() != null ? booking.getVoucherDiscountMoney() : BigDecimal.ZERO)
                .totalPrice(booking.getTotalAmount())
                .roomAmount(totalRoomAmount)
                .roomName(mainDetail != null ? mainDetail.getRoomName() : "N/A")
                .roomTypeName(roomTypeName)
                .pricePerNight(roomPricePerNight)
                .totalNights(totalNights)
                .services(servicesUsed)
                .build();
    }
}