/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dto.request;

import com.app.enums.BookingSource;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ApiBookingRequestDTO {

    public static class ServiceOrderDTO {

        @NotNull(message = "Thiếu ID dịch vụ")
        private Integer serviceId;

        @NotNull(message = "Thiếu số lượng")
        @Min(value = 1, message = "Số lượng dịch vụ phải từ 1 trở lên")
        private Integer quantity;

        public ServiceOrderDTO() {
        }

        public ServiceOrderDTO(Integer serviceId, Integer quantity) {
            this.serviceId = serviceId;
            this.quantity = quantity;
        }

        /**
         * @return the serviceId
         */
        public Integer getServiceId() {
            return serviceId;
        }

        /**
         * @param serviceId the serviceId to set
         */
        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        /**
         * @return the quantity
         */
        public Integer getQuantity() {
            return quantity;
        }

        /**
         * @param quantity the quantity to set
         */
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
        
    }

    @NotNull(message = "Thiếu ID phòng")
    private Integer roomId;
    
    @NotNull(message = "Thiếu ID người đặt")
    private Integer userBookingId;
    
    private Integer voucherId;

    @NotNull(message = "Thiếu ngày nhận phòng")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date checkIn;

    @NotNull(message = "Thiếu ngày trả phòng")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date checkOut;

    @NotNull(message = "Thiếu giá tiền")
    private BigDecimal clientPrice;

    @NotNull(message = "Thiếu số lượng người")
    @Min(value = 1, message = "Số lượng khách hàng không thể bé hơn 1")
    private Integer clientCapacity;

    @NotNull(message = "Thiếu phương thức thanh toán")
    private Integer paymentMethodId;

    @NotNull(message = "Thiếu nguồn đặt phòng")
    private BookingSource bookingSource;

    private String note;

    // Optional
    @Valid
    private List<ServiceOrderDTO> services;

    public ApiBookingRequestDTO() {
    }

    public ApiBookingRequestDTO(Integer roomId, Integer userBookingId, Date checkIn, Date checkOut, BigDecimal clientPrice, Integer clientCapacity, Integer paymentMethodId, BookingSource bookingSource, String note, List<ServiceOrderDTO> services) {
        this.roomId = roomId;
        this.userBookingId = userBookingId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.clientPrice = clientPrice;
        this.clientCapacity = clientCapacity;
        this.paymentMethodId = paymentMethodId;
        this.bookingSource = bookingSource;
        this.note = note;
        this.services = services;
    }

    /**
     * @return the roomId
     */
    public Integer getRoomId() {
        return roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the userBookingId
     */
    public Integer getUserBookingId() {
        return userBookingId;
    }

    /**
     * @param userBookingId the userBookingId to set
     */
    public void setUserBookingId(Integer userBookingId) {
        this.userBookingId = userBookingId;
    }

    /**
     * @return the checkIn
     */
    public Date getCheckIn() {
        return checkIn;
    }

    /**
     * @param checkIn the checkIn to set
     */
    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * @return the checkOut
     */
    public Date getCheckOut() {
        return checkOut;
    }

    /**
     * @param checkOut the checkOut to set
     */
    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * @return the clientPrice
     */
    public BigDecimal getClientPrice() {
        return clientPrice;
    }

    /**
     * @param clientPrice the clientPrice to set
     */
    public void setClientPrice(BigDecimal clientPrice) {
        this.clientPrice = clientPrice;
    }

    /**
     * @return the clientCapacity
     */
    public Integer getClientCapacity() {
        return clientCapacity;
    }

    /**
     * @param clientCapacity the clientCapacity to set
     */
    public void setClientCapacity(Integer clientCapacity) {
        this.clientCapacity = clientCapacity;
    }

    /**
     * @return the paymentMethodId
     */
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * @param paymentMethodId the paymentMethodId to set
     */
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /**
     * @return the bookingSource
     */
    public BookingSource getBookingSource() {
        return bookingSource;
    }

    /**
     * @param bookingSource the bookingSource to set
     */
    public void setBookingSource(BookingSource bookingSource) {
        this.bookingSource = bookingSource;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the services
     */
    public List<ServiceOrderDTO> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List<ServiceOrderDTO> services) {
        this.services = services;
    }

    /**
     * @return the voucherId
     */
    public Integer getVoucherId() {
        return voucherId;
    }

    /**
     * @param voucherId the voucherId to set
     */
    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }
    
    
    
}
