package com.app.controllers.client;

import com.app.dto.response.ApiResponse;
import com.app.dto.response.ApiServiceDTO;
import com.app.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/services")
    public ResponseEntity<?> getServicesForClient() {
        try {
            List<ApiServiceDTO> serviceList = this.serviceService.getActiveServicesForClient();
            
            ApiResponse<List<ApiServiceDTO>> response = new ApiResponse<>(200, "Lấy danh sách dịch vụ thành công", serviceList);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(500, "Lỗi server: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}