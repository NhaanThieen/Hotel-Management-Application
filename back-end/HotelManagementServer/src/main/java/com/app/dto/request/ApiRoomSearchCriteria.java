/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


public class ApiRoomSearchCriteria {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Ngày nhận phòng không được để trống")
    private Date checkIn;
    private Date checkOut;
    private Integer roomTypeId;
    private Double minPrice;
    private Double maxPrice;
    @NotNull(message = "Số trang không được null")
    @Min(value = 1, message = "Số trang không được là 0 hoặc âm")
    private Integer page;

    public ApiRoomSearchCriteria() {
    }

    public ApiRoomSearchCriteria(Date checkIn, Date checkOut, Integer roomTypeId, Double minPrice, Double maxPrice, Integer page) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomTypeId = roomTypeId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.page = page;
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
     * @return the roomTypeId
     */
    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    /**
     * @param roomTypeId the roomTypeId to set
     */
    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    /**
     * @return the minPrice
     */
    public Double getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @return the maxPrice
     */
    public Double getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }
    
    
}
