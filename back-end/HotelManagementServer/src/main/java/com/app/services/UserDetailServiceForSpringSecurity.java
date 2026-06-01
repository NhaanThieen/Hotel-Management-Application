/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services;

import org.springframework.security.core.userdetails.UserDetailsService;

// Extend class này để Spring Security có thể lấy user bằng username.
public interface UserDetailServiceForSpringSecurity extends UserDetailsService {

}
