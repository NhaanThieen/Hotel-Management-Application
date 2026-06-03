/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.controllers.client;

import com.app.dto.request.ApiBookingRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiBookingController {

    @PostMapping("/secure/booking/process")
    public ResponseEntity<?> processBooking(@Valid @RequestBody ApiBookingRequestDTO request) {
        // Gọi Template Method (AbstractBookingProcess) ở đây
        return null;
    }
}
