
package com.app.dto.request;

import java.math.BigDecimal;


public class RoomSearchCriteria {
    private String name;
    private Integer statusId;
    private Integer typeId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer page = 1;

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
     * @return the minPrice
     */
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @return the maxPrice
     */
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }
}
