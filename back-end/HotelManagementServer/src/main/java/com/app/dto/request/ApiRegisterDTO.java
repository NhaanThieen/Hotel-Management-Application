/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class ApiRegisterDTO {
    @NotBlank(message = "Tên người dùng không được trống")
    private String name;
    
    @NotBlank(message = "Username không được trống")
    private String username;
    
    @NotBlank(message = "Mật khẩu không được trống")
    private String password;
    
    @NotBlank(message = "Mật khẩu không được trống")
    private String confirmPassword;
    
    @NotBlank(message = "Số điện thoại không được trống")
    @Pattern(regexp = "^\\d+$", message = "Số điện thoại chỉ được chứa các chữ số")
    private String phone;

    public ApiRegisterDTO() {
    }

    public ApiRegisterDTO(String name, String username, String password, String confirmPassword, String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
