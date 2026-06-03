package com.app.dto.response.mybooking;
import java.math.BigDecimal;

public class MyBookingHistoryResponse {
    private Integer id;
    private String type; 
    private String title;
    private String badgeText;
    private String badgeBg;
    private String subText;
    private BigDecimal amount;
    private MyBookingHistoryRawResponse raw;
    private Boolean isReviewed;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBadgeText() { return badgeText; }
    public void setBadgeText(String badgeText) { this.badgeText = badgeText; }
    public String getBadgeBg() { return badgeBg; }
    public void setBadgeBg(String badgeBg) { this.badgeBg = badgeBg; }
    public String getSubText() { return subText; }
    public void setSubText(String subText) { this.subText = subText; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public MyBookingHistoryRawResponse getRaw() { return raw; }
    public void setRaw(MyBookingHistoryRawResponse raw) { this.raw = raw; }
    public Boolean getIsReviewed() { return isReviewed; }
    public void setIsReviewed(Boolean isReviewed) { this.isReviewed = isReviewed; }
}