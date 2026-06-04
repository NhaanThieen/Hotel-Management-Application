package com.app.controllers.client;

import com.app.dto.request.PaymentRequestDTO;
import com.app.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-vnpay")
    public ResponseEntity<Map<String, Object>> createVnPay(@Valid @RequestBody PaymentRequestDTO requestDTO, HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String ipAddress = request.getRemoteAddr();
            String paymentUrl = paymentService.createVnPayUrl(requestDTO, ipAddress);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("url", paymentUrl);

            response.put("status", 200);
            response.put("message", "Tạo URL thanh toán VNPay thành công");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", 500);
            response.put("message", "Lỗi Server: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/create-zalopay")
    public ResponseEntity<Map<String, Object>> createZaloPay(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String paymentUrl = paymentService.createZaloPayUrl(requestDTO);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("url", paymentUrl);

            response.put("status", 200);
            response.put("message", "Tạo URL thanh toán ZaloPay thành công");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", 500);
            response.put("message", "Lỗi Server: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}