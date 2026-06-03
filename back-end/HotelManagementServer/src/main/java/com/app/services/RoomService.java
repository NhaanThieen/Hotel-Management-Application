/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.services;

import com.app.dto.request.ApiRoomSearchCriteria;
import com.app.dto.request.RoomCreateDTO;
import com.app.dto.request.RoomSearchCriteria;
import com.app.dto.response.ApiRoomPageDTO;
import com.app.dto.response.ListRoomAdminRoomPageDTO;



public interface RoomService {
    public ListRoomAdminRoomPageDTO getRooms(RoomSearchCriteria roomData);
    public ApiRoomPageDTO getRoomsForClient(ApiRoomSearchCriteria roomData);
    public void createRooms(RoomCreateDTO roomDTO);
}
