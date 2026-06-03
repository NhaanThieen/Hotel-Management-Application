package com.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ApiGoogleLoginDTO {
    @NotBlank(message = "Google Token không được để trống")
    private String token;

    public ApiGoogleLoginDTO() {}

    public ApiGoogleLoginDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}