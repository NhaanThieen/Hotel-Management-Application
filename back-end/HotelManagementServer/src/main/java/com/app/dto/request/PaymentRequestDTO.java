package com.app.dto.request;

import java.math.BigDecimal;

public class PaymentRequestDTO {
    private Integer bookingId;
    private BigDecimal totalAmount;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}