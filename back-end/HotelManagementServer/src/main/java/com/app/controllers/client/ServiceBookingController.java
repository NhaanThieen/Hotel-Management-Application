package com.app.controllers.client;

import com.app.dto.request.ServiceBookingRequest;
import com.app.services.ServiceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/secure/service-booking")
@CrossOrigin
public class ServiceBookingController {

    @Autowired
    private ServiceBookingService serviceBookingService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addServiceToBooking(@RequestBody ServiceBookingRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            Integer receiptId = serviceBookingService.addServiceToBooking(request);
            
            response.put("status", 200);
            response.put("message", "Đăng ký dịch vụ thành công!");
            
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("receiptId", receiptId);
            response.put("data", data);
            
            return ResponseEntity.ok(response);
            
        } catch (NoSuchElementException ex) {
            response.put("status", 404);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (IllegalStateException ex) {
            response.put("status", 409); 
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (Exception ex) {
            response.put("status", 500);
            response.put("message", "Lỗi Server: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}