package com.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class RoomCreateDTO {

    public static class BedRoomDTO {

        @NotNull(message = "Loại giường không được trống")
        private Integer bedTypeId;

        @NotNull(message = "Số lượng loại giường không được trống")
        @Min(value = 0, message = "Số lượng loại giường không được âm")
        private Integer quantity;

        public BedRoomDTO() {
        }

        /**
         * @return the bedTypeId
         */
        public Integer getBedTypeId() {
            return bedTypeId;
        }

        /**
         * @param bedTypeId the bedTypeId to set
         */
        public void setBedTypeId(Integer bedTypeId) {
            this.bedTypeId = bedTypeId;
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

    // Sử dụng kiểu Wrapper để nếu không có giá trị thì sẽ là null thay vì 0 hay rỗng như kiểu nguyên thủy
    private Integer roomId;

    // NotBlank chỉ dành cho xử lý chuỗi
    @NotBlank(message = "Tên phòng không được trống")
    private String name;

    @NotNull(message = "Giá phòng không được trống")
    @Min(value = 0, message = "Giá phòng không được âm")
    private BigDecimal price;

    @NotNull(message = "Loại phòng không được trống")
    private Integer typeId;

    @NotNull(message = "Trạng thái phòng không được trống")
    private Integer statusId;

    @NotNull(message = "Số lượng người trong phòng không được trống")
    @Min(value = 1, message = "Số lượng người phải lớn hơn 0")
    private Integer capacity;

    @Valid
    private List<BedRoomDTO> beds = new ArrayList<>();

    private MultipartFile thumbnailImage;
    
    @Size(max=10, message = "Chỉ được phép tải lên tối đa 10 ảnh phụ")
    private List<MultipartFile> extraImageFiles;

    public RoomCreateDTO() {
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
     * @return the typeId
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the statusId
     */
    public Integer getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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
    public List<BedRoomDTO> getBeds() {
        return beds;
    }

    /**
     * @param beds the beds to set
     */
    public void setBeds(List<BedRoomDTO> beds) {
        this.beds = beds;
    }

    /**
     * @return the thumbnailImage
     */
    public MultipartFile getThumbnailImage() {
        return thumbnailImage;
    }

    /**
     * @param thumbnailImage the thumbnailImage to set
     */
    public void setThumbnailImage(MultipartFile thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    /**
     * @return the extraImageFiles
     */
    public List<MultipartFile> getExtraImageFiles() {
        return extraImageFiles;
    }

    /**
     * @param extraImageFiles the extraImageFiles to set
     */
    public void setExtraImageFiles(List<MultipartFile> extraImageFiles) {
        this.extraImageFiles = extraImageFiles;
    }
}
