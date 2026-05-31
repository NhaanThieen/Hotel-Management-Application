/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services.servicesImpl;

import com.app.pojo.Roomimage;
import com.app.repositories.RoomImageRepository;
import com.app.services.RoomImageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoomImageServiceImpl implements RoomImageService{

    @Autowired
    private RoomImageRepository roomImageRepository;
    
    @Override
    @Transactional
    public List<Roomimage> saveAll(List<Roomimage> roomImages) {
        return this.roomImageRepository.saveAll(roomImages);
    }
    
}
