/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// Cho phép sử dụng TransactionManager của Spring
@EnableTransactionManagement
@ComponentScan(
        basePackages = {
            "com.app.repositories",
            "com.app.services",
            "com.app.dto",
            "com.app.filters",
            "com.app.utils"
        }
)
public class RootAppContextConfig {

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "doa6ykcp1",
                        "api_key", "932522258646968",
                        "api_secret", "n4WGj7TbCtEqMDhwz1lf4irdNHk",
                        "secure", true));
        return cloudinary;
    }
}
