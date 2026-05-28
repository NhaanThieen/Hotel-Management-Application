
package com.app.dto.response;

import com.app.pojo.Roomtype;
import java.util.List;


public class RoomTypeResponse {
    private List<Roomtype> roomTypes;

    public RoomTypeResponse(List<Roomtype> roomTypes) {
        this.roomTypes = roomTypes;
    }

    /**
     * @return the roomTypes
     */
    public List<Roomtype> getRoomTypes() {
        return roomTypes;
    }

    /**
     * @param roomTypes the roomTypes to set
     */
    public void setRoomTypes(List<Roomtype> roomTypes) {
        this.roomTypes = roomTypes;
    }
    
    
    
}
