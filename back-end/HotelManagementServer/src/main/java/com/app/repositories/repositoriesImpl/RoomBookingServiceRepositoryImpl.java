/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roombookingservice;
import com.app.repositories.RoomBookingServiceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomBookingServiceRepositoryImpl implements RoomBookingServiceRepository{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Roombookingservice saveRoomBookingService(Roombookingservice rbs) {
        Session s = sessionFactory.getCurrentSession();
        s.persist(rbs);
        return rbs;
    }
}
