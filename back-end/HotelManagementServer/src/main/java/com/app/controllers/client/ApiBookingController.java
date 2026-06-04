package com.app.controllers.client;

import com.app.dto.request.ApiBookingRequestDTO;
import com.app.dto.request.PaymentRequestDTO;
import com.app.services.RoomBookingService;
import com.app.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
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

    // Tiêm PaymentService vào đây để dùng
    @Autowired
    private PaymentService paymentService; 

    @PostMapping("/secure/booking/process")
    public ResponseEntity<?> processBooking(@Valid @RequestBody ApiBookingRequestDTO request, HttpServletRequest httpRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 1. Chạy Template Method để lưu đơn hàng
            String bookingIdStr = roomBookingService.processBooking(request);
            String paymentUrl = "";
            
            // 2. Tự động sinh link thanh toán dựa trên kết quả trả về
            try {
                // Ép kiểu ID đơn hàng để truyền vào PaymentService
                Integer bookingId = Integer.parseInt(bookingIdStr);
                
                if (request.getPaymentMethodId() == 2) { // Nếu là VNPay
                    PaymentRequestDTO payReq = new PaymentRequestDTO();
                    payReq.setBookingId(bookingId);
                    payReq.setTotalAmount(request.getClientPrice()); 
                    paymentUrl = paymentService.createVnPayUrl(payReq, httpRequest.getRemoteAddr());
                } else if (request.getPaymentMethodId() == 3) { // Nếu là ZaloPay
                    PaymentRequestDTO payReq = new PaymentRequestDTO();
                    payReq.setBookingId(bookingId);
                    payReq.setTotalAmount(request.getClientPrice());
                    paymentUrl = paymentService.createZaloPayUrl(payReq);
                }
            } catch (NumberFormatException ex) {
                // Nếu Template Method của bạn đã tự sinh URL và trả về chuỗi thì dùng luôn
                paymentUrl = bookingIdStr; 
            }

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