/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories;

import com.app.pojo.Service;
import java.util.List;


public interface ServiceRepository {
    public Service getServiceById(Integer id);
    public Integer deductStock(Integer serviceId, Integer quantity);
    public List<com.app.pojo.Service> getActiveServices();
}

