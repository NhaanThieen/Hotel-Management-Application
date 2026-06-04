/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services;
import com.app.dto.request.PaymentRequestDTO;
/**
 *
 * @author thien
 */
public interface PaymentService {
    String createVnPayUrl(PaymentRequestDTO request, String ipAddress);
    String createZaloPayUrl(PaymentRequestDTO request);
}