package com.app.controllers.client;

import com.app.dto.request.ApiGoogleLoginDTO;
import com.app.dto.response.ApiResponse;
import com.app.pojo.User;
import com.app.services.UserService;
import com.app.utils.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGoogleLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    private static final String GOOGLE_CLIENT_ID = "609164908222-rkgbsv9trsv3efdj8hieptmrm30gl2s6.apps.googleusercontent.com";

    @PostMapping("/auth/google")
    public ResponseEntity<?> googleLogin(@RequestBody ApiGoogleLoginDTO googleLoginDTO) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(googleLoginDTO.getToken());
            
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");
                
                User user = this.userService.processGoogleUser(email, name);

                String systemToken = jwtUtils.generateToken(user.getUsername(), "ROLE_CLIENT");

                Map<String, String> data = new HashMap<>();
                data.put("token", systemToken);
                
                ApiResponse<Map<String, String>> response = new ApiResponse<>(200, "Đăng nhập Google thành công", data);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(401, "Token Google không hợp lệ hoặc đã hết hạn", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Lỗi hệ thống xử lý Google Login: " + e.getMessage(), null));
        }
    }
}