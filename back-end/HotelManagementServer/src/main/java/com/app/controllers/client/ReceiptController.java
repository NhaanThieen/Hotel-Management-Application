package com.app.controllers.client;

import com.app.dto.response.ReceiptResponseDTO;
import com.app.services.MyBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class ReceiptController {

    @Autowired
    private MyBookingService myBookingService;

    @GetMapping("/receipts/{id}")
    public ResponseEntity<?> getReceiptById(@PathVariable("id") Integer id) {
        try {
            ReceiptResponseDTO receipt = myBookingService.getReceiptDetail(id);
            
            return ResponseEntity.ok(receipt); 
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", e.getMessage()));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("message", "Lỗi máy chủ: " + e.getMessage()));
        }
    }
}