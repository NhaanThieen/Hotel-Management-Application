/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.pattern.templatemethod;

import com.app.dto.request.ApiBookingRequestDTO;
import com.app.pojo.Room;
import com.app.pojo.Roombooking;


public abstract class AbstractBookingProcess {
    
    protected abstract Room getRoomById(Integer roomId);
    protected abstract void validateData(Room roomDb, ApiBookingRequestDTO request);
    protected abstract void checkRoomAvailability(ApiBookingRequestDTO request);
    protected abstract void lockRoom(Room room);
    protected abstract Roombooking saveToDb(Room room, ApiBookingRequestDTO request);
    protected abstract String executePayment(Roombooking booking, Integer paymentMethodId);
    
    public final String processBooking(ApiBookingRequestDTO request){
        // B0: Lấy đối tượng lên
        Room room = getRoomById(request.getRoomId());
        // B1: Kiểm tra toàn vẹn dữ liệu
        this.validateData(room, request);
        // B2: Kiểm tra phòng trống
        checkRoomAvailability(request);
        // B3: Khóa phòng tránh tranh chấp
        lockRoom(room);                   
        // B4: Lưu db
        Roombooking booking = saveToDb(room, request);
        // B5: Trả về URL thanh toán
        return executePayment(booking, request.getPaymentMethodId());
    }
}
