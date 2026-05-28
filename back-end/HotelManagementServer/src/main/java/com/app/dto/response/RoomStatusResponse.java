/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dto.response;

import com.app.pojo.Roomstatus;
import java.util.List;

/**
 *
 * @author Nhaan
 */
public class RoomStatusResponse {
    private List<Roomstatus> roomStatus;

    public RoomStatusResponse(List<Roomstatus> roomStatus) {
        this.roomStatus = roomStatus;
    }

    /**
     * @return the roomStatus
     */
    public List<Roomstatus> getRoomStatus() {
        return roomStatus;
    }

    /**
     * @param roomStatus the roomStatus to set
     */
    public void setRoomStatus(List<Roomstatus> roomStatus) {
        this.roomStatus = roomStatus;
    }
    
    
}
