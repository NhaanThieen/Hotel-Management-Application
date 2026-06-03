/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.services;

import com.app.dto.request.ApiBookingRequestDTO;


public interface RoomBookingService {
    // Trả về chuỗi URL thanh toán
    String processBooking(ApiBookingRequestDTO request);
}
