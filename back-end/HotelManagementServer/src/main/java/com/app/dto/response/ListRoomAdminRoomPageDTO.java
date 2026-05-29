package com.app.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ListRoomAdminRoomPageDTO {

    public static class BedForAdminRoomPageDTO {

        private String name;
        private Integer quantity;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the quantity
         */
        public Integer getQuantity() {
            return quantity;
        }

        /**
         * @param quantity the quantity to set
         */
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
        
        
    }

    public static class RoomForAdminRoomPageDTO {

        private Integer roomId;
        private String name;
        private String imgURL;
        private String roomTypeName;
        private Integer roomTypeId;
        private Integer capacity;
        private List<BedForAdminRoomPageDTO> beds;
        private BigDecimal price;
        private String roomStatusName;
        private Integer roomStatusId;

        /**
         * @return the roomId
         */
        public Integer getRoomId() {
            return roomId;
        }

        /**
         * @param roomId the roomId to set
         */
        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the imgURL
         */
        public String getImgURL() {
            return imgURL;
        }

        /**
         * @param imgURL the imgURL to set
         */
        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        /**
         * @return the roomTypeName
         */
        public String getRoomTypeName() {
            return roomTypeName;
        }

        /**
         * @param roomTypeName the roomTypeName to set
         */
        public void setRoomTypeName(String roomTypeName) {
            this.roomTypeName = roomTypeName;
        }

        /**
         * @return the capacity
         */
        public Integer getCapacity() {
            return capacity;
        }

        /**
         * @param capacity the capacity to set
         */
        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        /**
         * @return the beds
         */
        public List<BedForAdminRoomPageDTO> getBeds() {
            return beds;
        }

        /**
         * @param beds the beds to set
         */
        public void setBeds(List<BedForAdminRoomPageDTO> beds) {
            this.beds = beds;
        }

        /**
         * @return the price
         */
        public BigDecimal getPrice() {
            return price;
        }

        /**
         * @param price the price to set
         */
        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        /**
         * @return the roomStatusName
         */
        public String getRoomStatusName() {
            return roomStatusName;
        }

        /**
         * @param roomStatusName the roomStatusName to set
         */
        public void setRoomStatusName(String roomStatusName) {
            this.roomStatusName = roomStatusName;
        }

        /**
         * @return the roomTypeId
         */
        public Integer getRoomTypeId() {
            return roomTypeId;
        }

        /**
         * @param roomTypeId the roomTypeId to set
         */
        public void setRoomTypeId(Integer roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        /**
         * @return the roomStatusId
         */
        public Integer getRoomStatusId() {
            return roomStatusId;
        }

        /**
         * @param roomStatusId the roomStatusId to set
         */
        public void setRoomStatusId(Integer roomStatusId) {
            this.roomStatusId = roomStatusId;
        }
    }

    private List<RoomForAdminRoomPageDTO> rooms;
    private int currentPage;

    public ListRoomAdminRoomPageDTO(List<RoomForAdminRoomPageDTO> rooms) {
        this.rooms = rooms;
        
    }
    /**
     * @return the rooms
     */
    public List<RoomForAdminRoomPageDTO> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomForAdminRoomPageDTO> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    
}
