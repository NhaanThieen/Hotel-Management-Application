package com.app.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptResponseDTO {
    private Integer receiptId;
    private String userName;
    private String userPhone;
    private String userPaidId;
    private String timeCheckIn;
    private String timeCheckOut;
    private String staffId;
    private String staffName;
    private BigDecimal vipDiscountAmount;
    private BigDecimal totalPrice;
    private BigDecimal roomAmount;
    private String roomName;
    private String roomTypeName;
    private BigDecimal pricePerNight;
    private Integer totalNights;
    private List<ServiceItemDTO> services;

    @Data
    @Builder
    public static class ServiceItemDTO {
        private String serviceName;
        private Integer quantity;
        private BigDecimal unitPriceAtUse; 
    }
}