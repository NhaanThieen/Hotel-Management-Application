/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.controllers.client;

import com.app.dto.request.ApiLoginDTO;
import com.app.dto.response.ApiResponse;
import com.app.utils.JwtUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ApiLoginDTO loginDTO) {

        try {
            // Sử dụng AuthenticationManager để check pass tự động
            Authentication auth = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            // Tạo token từ thông tin của user
            String roles = auth.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.joining(","));

            String token = jwtUtils.generateToken(auth.getName(), roles);

            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            ApiResponse<Map<String, String>> response = new ApiResponse<>(200, "Đăng nhập thành công", data);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(401, "Tài khoản hoặc mật khẩu không chính xác", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(500, "Lỗi server: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
