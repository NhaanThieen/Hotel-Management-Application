package com.app.services;
import com.app.dto.request.ServiceBookingRequest;

public interface ServiceBookingService {
    Integer addServiceToBooking(ServiceBookingRequest request);
}