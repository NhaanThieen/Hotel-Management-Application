/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services.servicesImpl;

import com.app.dto.request.RoomCreateDTO;
import com.app.dto.request.RoomSearchCriteria;
import com.app.dto.response.ListRoomAdminRoomPageDTO;
import com.app.pojo.Bed;
import com.app.pojo.Room;
import com.app.pojo.Roomstatus;
import com.app.pojo.Roomtype;
import com.app.repositories.RoomRepository;
import com.app.repositories.RoomStatusRepository;
import com.app.repositories.RoomTypeRepository;
import com.app.services.RoomService;
import java.util.ArrayList;
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

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public ListRoomAdminRoomPageDTO getRooms(RoomSearchCriteria roomData) {
        int currentPage = roomData.getPage();
        if (currentPage < 1) {
            currentPage = 1;
        }
        // Lấy rooms entity từ repo
        List<Room> roomEntities = this.roomRepository.getRooms(roomData);

        // Mapping roomEntities sang dto
        List<ListRoomAdminRoomPageDTO.RoomForAdminRoomPageDTO> roomDTOs = new ArrayList<>();

        for (Room entity : roomEntities) {
            ListRoomAdminRoomPageDTO.RoomForAdminRoomPageDTO roomDTO = new ListRoomAdminRoomPageDTO.RoomForAdminRoomPageDTO();

            roomDTO.setRoomId(entity.getRoomId());
            roomDTO.setName(entity.getRoomName());
            roomDTO.setImgURL(entity.getThumbnail());
            roomDTO.setRoomTypeId(entity.getRoomTypeId().getRoomTypeId());
            roomDTO.setRoomTypeName(entity.getRoomTypeId().getName());
            roomDTO.setCapacity(entity.getCapacity());

            List<ListRoomAdminRoomPageDTO.BedForAdminRoomPageDTO> beds = new ArrayList<>();

            for (Bed bed : entity.getBedList()) {
                ListRoomAdminRoomPageDTO.BedForAdminRoomPageDTO bedDTO = new ListRoomAdminRoomPageDTO.BedForAdminRoomPageDTO();
                bedDTO.setName(bed.getBedTypeId().getName());
                bedDTO.setQuantity(bed.getAmount());
                beds.add(bedDTO);
            }

            roomDTO.setBeds(beds);
            roomDTO.setPrice(entity.getPrice());
            roomDTO.setRoomStatusName(entity.getRoomStatusId().getName());
            roomDTO.setRoomStatusId(entity.getRoomStatusId().getRoomStatusId());
            roomDTOs.add(roomDTO);
        }

        ListRoomAdminRoomPageDTO response = new ListRoomAdminRoomPageDTO(roomDTOs);

        response.setCurrentPage(currentPage);
        return response;
    }

    @Override
    @Transactional
    public void createRooms(RoomCreateDTO roomDTO) {

        // Lấy roomStatus và roomType
        Roomstatus rs = this.roomStatusRepository.getRoomStatusById(roomDTO.getStatusId());
        Roomtype rt = this.roomTypeRepository.getRoomTypeById(roomDTO.getTypeId());

        // Chuyển đổi roomDTO thành room
        Room room = new Room();

        if (rs == null) {
            throw new IllegalArgumentException("RoomStatus không được null");
        }
        if (rt == null) {
            throw new IllegalArgumentException("RoomType không được null");
        }
        // <0 là A < B
        if (roomDTO.getPrice().compareTo(rt.getPrice()) < 0) {
            throw new IllegalArgumentException(String.format("Số tiền không được nhỏ hơn %s", rt.getPrice()));
        }
        room.setRoomName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setRoomStatusId(rs);
        room.setRoomTypeId(rt);
        room.setPrice(roomDTO.getPrice());
        room.setIsDeleted((short) 0);
        room.setVersion(0);

        // Save room
        this.roomRepository.saveRoom(room);
    }
}
