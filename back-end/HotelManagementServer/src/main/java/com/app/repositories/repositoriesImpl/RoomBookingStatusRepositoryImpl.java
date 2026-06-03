/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roombookingstatus;
import com.app.repositories.RoomBookingStatusRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomBookingStatusRepositoryImpl implements RoomBookingStatusRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Roombookingstatus getRoomBookingStatusByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Roombookingstatus> query = session.createQuery("FROM Roombookingstatus WHERE LOWER(name) = LOWER(:name)", Roombookingstatus.class);
        query.setParameter("name", name);
        return query.getSingleResultOrNull();
    }
}
