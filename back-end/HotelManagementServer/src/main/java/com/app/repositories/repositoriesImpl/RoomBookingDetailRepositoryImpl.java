/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roombookingdetail;
import com.app.repositories.RoomBookingDetailRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomBookingDetailRepositoryImpl implements RoomBookingDetailRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Roombookingdetail saveRoomBookingDetail(Roombookingdetail rbd) {
        Session s = sessionFactory.getCurrentSession();
        s.persist(rbd);
        return rbd;
    }
}
