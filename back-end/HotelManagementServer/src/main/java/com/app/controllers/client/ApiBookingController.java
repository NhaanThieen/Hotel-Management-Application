/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.controllers.client;

import com.app.dto.request.ApiBookingRequestDTO;
import com.app.services.RoomBookingService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiBookingController {
    
    @Autowired
    private RoomBookingService roomBookingService;

    @PostMapping("/secure/booking/process")
    public ResponseEntity<?> processBooking(@Valid @RequestBody ApiBookingRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Gọi Interface (Nó sẽ tự chui vào Abstract -> chạy luồng 5 bước của Template Method)
            String paymentUrl = roomBookingService.processBooking(request);

            response.put("status", "success");
            response.put("message", "Khởi tạo đơn đặt phòng thành công.");
            response.put("paymentUrl", paymentUrl); 
            
            return ResponseEntity.ok(response); 

        } catch (IllegalArgumentException | IllegalStateException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response); 

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response); 
        }
    }
}
