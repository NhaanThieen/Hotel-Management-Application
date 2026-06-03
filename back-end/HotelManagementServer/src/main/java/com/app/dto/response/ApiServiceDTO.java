package com.app.dto.response;

import java.math.BigDecimal;

public class ApiServiceDTO {
    private Integer serviceId;
    
    private String name;
    
    private BigDecimal price;
    
    private String imgURL;
    
    private String type;
    
    private Integer isDeleted;

    public ApiServiceDTO() {
    }

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImgURL() { return imgURL; }
    public void setImgURL(String imgURL) { this.imgURL = imgURL; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }
}