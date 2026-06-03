package com.app.repositories;

import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingservice;
import com.app.pojo.Service;

public interface ServiceBookingRepository {
    Roombooking getActiveBookingByUserName(String userName);
    Service getServiceById(Integer serviceId);
    void saveBookingService(Roombookingservice service);
}