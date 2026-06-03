/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories;

import com.app.dto.request.ApiRoomSearchCriteria;
import com.app.dto.request.RoomSearchCriteria;
import com.app.pojo.Room;
import java.util.Date;
import java.util.List;


public interface RoomRepository {
    public List<Room> getRooms(RoomSearchCriteria roomData);
    public List<Room> getRoomsForClient(ApiRoomSearchCriteria roomData);
    public Room getBasicRoomById(Integer roomId);
    public Room saveRoom(Room room);
    public Boolean isRoomBusy(Integer roomId, Date checkIn, Date checkOut);
    public void lockRoom(Room room);
    List<Room> getRoomsForReceptionist();
}
