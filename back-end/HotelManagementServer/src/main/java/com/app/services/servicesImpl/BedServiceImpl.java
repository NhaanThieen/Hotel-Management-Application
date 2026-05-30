/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services.servicesImpl;

import com.app.pojo.Bed;
import com.app.repositories.BedRepository;
import com.app.services.BedService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BedServiceImpl implements BedService{

    @Autowired
    private BedRepository bedRepository; 
    
    @Override
    @Transactional
    public List<Bed> saveAll(List<Bed> beds) {
        return this.bedRepository.saveAll(beds);
    }
}
