package com.app.controllers.client;

import com.app.dto.response.mybooking.MyBookingHistoryResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.services.MyBookingService;

@RestController
@RequestMapping("/api/secure/bookings")
public class MyBookingController {

    @Autowired
    private MyBookingService bookingService;

    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getBookingHistory(@RequestParam("userName") String userName) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            List<MyBookingHistoryResponse> history = bookingService.getBookingHistory(userName);
            response.put("status", 200);
            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", history); 
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("status", 500);
            response.put("message", "Lỗi Server: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelBooking(
            @PathVariable("id") Integer id, 
            @RequestParam("userName") String userName) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            bookingService.cancelBooking(id, userName);
            response.put("status", 200);
            response.put("message", "Hủy đơn đặt phòng thành công.");
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            response.put("status", 404);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException ex) {
            response.put("status", 409);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}