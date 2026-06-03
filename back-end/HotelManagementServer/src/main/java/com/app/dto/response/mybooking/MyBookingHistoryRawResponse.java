package com.app.dto.response.mybooking;
import java.math.BigDecimal;
import java.util.List;

public class MyBookingHistoryRawResponse {
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;
    private BigDecimal voucherDiscountMoney;
    private BigDecimal depositAmount;
    private String staffName;
    private List<MyBookingHistoryDetailResponse> details;

    public String getDayStart() { return dayStart; }
    public void setDayStart(String dayStart) { this.dayStart = dayStart; }
    public String getDayEnd() { return dayEnd; }
    public void setDayEnd(String dayEnd) { this.dayEnd = dayEnd; }
    public String getTimeStart() { return timeStart; }
    public void setTimeStart(String timeStart) { this.timeStart = timeStart; }
    public String getTimeEnd() { return timeEnd; }
    public void setTimeEnd(String timeEnd) { this.timeEnd = timeEnd; }
    public BigDecimal getVoucherDiscountMoney() { return voucherDiscountMoney; }
    public void setVoucherDiscountMoney(BigDecimal voucherDiscountMoney) { this.voucherDiscountMoney = voucherDiscountMoney; }
    public BigDecimal getDepositAmount() { return depositAmount; }
    public void setDepositAmount(BigDecimal depositAmount) { this.depositAmount = depositAmount; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public List<MyBookingHistoryDetailResponse> getDetails() { return details; }
    public void setDetails(List<MyBookingHistoryDetailResponse> details) { this.details = details; }
}