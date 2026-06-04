/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories;

import com.app.pojo.Roombooking;


public interface RoomBookingRepository {
    public Roombooking saveRoomBooking(Roombooking roomBooking);
    Roombooking getRoomBookingById(Integer id);
}
