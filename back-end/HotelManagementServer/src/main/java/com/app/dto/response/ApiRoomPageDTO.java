package com.app.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ApiRoomPageDTO {

    public static class BedDTO {

        private String name;
        private Integer quantity;

        public BedDTO() {
        }

        public BedDTO(String name, Integer quantity) {
            this.name = name;
            this.quantity = quantity;
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

    public static class RoomForClientDTO {

        private Integer roomId;
        private String name;
        private Integer roomTypeId;
        private String roomTypeName;
        private Integer capacity;
        private BigDecimal price;

        private String thumbnail;
        private List<String> galleryImages;

        private List<BedDTO> beds;

        public RoomForClientDTO() {
        }

        public RoomForClientDTO(Integer roomId, String name, Integer roomTypeId, String roomTypeName, Integer capacity, BigDecimal price, String thumbnail, List<String> galleryImages, List<BedDTO> beds) {
            this.roomId = roomId;
            this.name = name;
            this.roomTypeId = roomTypeId;
            this.roomTypeName = roomTypeName;
            this.capacity = capacity;
            this.price = price;
            this.thumbnail = thumbnail;
            this.galleryImages = galleryImages;
            this.beds = beds;
        }

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
         * @return the thumbnail
         */
        public String getThumbnail() {
            return thumbnail;
        }

        /**
         * @param thumbnail the thumbnail to set
         */
        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        /**
         * @return the galleryImages
         */
        public List<String> getGalleryImages() {
            return galleryImages;
        }

        /**
         * @param galleryImages the galleryImages to set
         */
        public void setGalleryImages(List<String> galleryImages) {
            this.galleryImages = galleryImages;
        }

        /**
         * @return the beds
         */
        public List<BedDTO> getBeds() {
            return beds;
        }

        /**
         * @param beds the beds to set
         */
        public void setBeds(List<BedDTO> beds) {
            this.beds = beds;
        }
    }

    private List<RoomForClientDTO> rooms;
    private int currentPage;

    public ApiRoomPageDTO() {
    }

    public ApiRoomPageDTO(List<RoomForClientDTO> rooms, int currentPage) {
        this.rooms = rooms;
        this.currentPage = currentPage;
    }

    /**
     * @return the rooms
     */
    public List<RoomForClientDTO> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomForClientDTO> rooms) {
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
