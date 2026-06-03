package com.app.dto.response.mybooking;
import java.math.BigDecimal;

public class MyBookingHistoryDetailResponse {
    private Integer roomBookingDetailId;
    private String roomName;
    private BigDecimal price;

    public Integer getRoomBookingDetailId() { return roomBookingDetailId; }
    public void setRoomBookingDetailId(Integer roomBookingDetailId) { this.roomBookingDetailId = roomBookingDetailId; }
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}