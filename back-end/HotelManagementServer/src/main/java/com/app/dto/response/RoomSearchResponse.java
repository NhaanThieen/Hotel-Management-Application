
package com.app.dto.response;

import com.app.pojo.Room;
import java.util.List;

public class RoomSearchResponse {

    private List<Room> rooms;

    public RoomSearchResponse(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
