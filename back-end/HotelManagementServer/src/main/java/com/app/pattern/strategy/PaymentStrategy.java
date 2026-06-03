/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.pattern.strategy;

import com.app.pojo.Roombooking;


public interface PaymentStrategy {
    // Trả về URL
    public String pay(Roombooking roomBooking);
}
