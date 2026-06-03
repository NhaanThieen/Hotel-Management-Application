/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;
import com.app.pojo.Roombooking;
import com.app.repositories.RoomBookingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomBookingRepositoryImpl implements RoomBookingRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Roombooking saveRoomBooking(Roombooking roomBooking) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(roomBooking);
        return roomBooking;
    }

    @Override
    public Roombooking getRoomBookingById(Integer id) {
        return this.sessionFactory.getCurrentSession().get(Roombooking.class, id);
    }
}
