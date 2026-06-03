package com.app.repositories;
import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingstatus;
import java.util.List;

public interface MyBookingRepository {
    List<Roombooking> getBookingsByUserName(String userName);
    Roombooking getBookingByIdAndUserName(Integer bookingId, String userName);
    Roombookingstatus getBookingStatusByName(String statusName);
}