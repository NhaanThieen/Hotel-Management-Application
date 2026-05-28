/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services.servicesImpl;

import com.app.pojo.Roomstatus;
import com.app.repositories.RoomStatusRepository;
import com.app.services.RoomStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)

public class RoomStatusServiceImpl implements RoomStatusService{
    
    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Override
    public List<Roomstatus> getRoomStatus() {
        return this.roomStatusRepository.getRoomStatus();
    }
}
