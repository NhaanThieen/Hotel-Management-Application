package com.app.pattern.templatemethod;

import com.app.dto.request.ApiBookingRequestDTO;
import com.app.pojo.Room;
import com.app.pojo.Roombooking;
import java.math.BigDecimal;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractBookingProcess {

    protected class BookingPriceContext {

        private BigDecimal roomPrice;
        private BigDecimal discountMoney;
        private BigDecimal totalAmount;

        public BookingPriceContext() {
        }

        
        public BookingPriceContext(BigDecimal roomPrice, BigDecimal discountMoney, BigDecimal totalAmount) {
            this.roomPrice = roomPrice;
            this.discountMoney = discountMoney;
            this.totalAmount = totalAmount;
        }

        public BigDecimal getRoomPrice() {
            return roomPrice;
        }

        public BigDecimal getDiscountMoney() {
            return discountMoney;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }
    }

    protected abstract Room getRoomById(Integer roomId);

    protected abstract BookingPriceContext validateData(Room roomDb, ApiBookingRequestDTO request);

    protected abstract void checkRoomAvailability(ApiBookingRequestDTO request);

    protected abstract void lockRoom(Room room);

    
    protected abstract Roombooking saveToDb(Room room, ApiBookingRequestDTO request, BookingPriceContext priceContext);

    protected abstract String executePayment(Roombooking booking, Integer paymentMethodId);

    public final String processBooking(ApiBookingRequestDTO request) {

        // B0: Lấy đối tượng lên
        Room room = getRoomById(request.getRoomId());
        
        // B1: Kiểm tra toàn vẹn dữ liệu 
        BookingPriceContext priceContext = this.validateData(room, request);
        
        // B2: Kiểm tra phòng trống
        checkRoomAvailability(request);
        
        // B3: Khóa phòng tránh tranh chấp
        lockRoom(room);
        
        // B4: Lưu db
        Roombooking booking = saveToDb(room, request, priceContext);
        
        // B5: Trả về URL thanh toán
        return executePayment(booking, request.getPaymentMethodId());
    }
}