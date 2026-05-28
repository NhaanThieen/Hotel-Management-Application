/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services.servicesImpl;

import com.app.dto.request.RoomSearchCriteria;
import com.app.pojo.Room;
import com.app.repositories.RoomRepository;
import com.app.services.RoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// Mặc định bật transaction read cho toàn bộ class, để tránh trường hợp gọi chéo hàm nội bộ không có trasaction
// Transaction read cũng tiết kiệm tài nguyên hơn transaciton write.
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms(RoomSearchCriteria roomData) {
        return this.roomRepository.getRooms(roomData);
    }
}
