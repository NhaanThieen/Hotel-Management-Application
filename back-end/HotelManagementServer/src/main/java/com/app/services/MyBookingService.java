package com.app.services;
import com.app.dto.response.ReceiptResponseDTO;
import com.app.dto.response.mybooking.MyBookingHistoryResponse;
import java.util.List;

public interface MyBookingService {
    List<MyBookingHistoryResponse> getBookingHistory(String userName);
    void cancelBooking(Integer bookingId, String userName);
    ReceiptResponseDTO getReceiptDetail(Integer bookingId);
}