/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roomtype;
import com.app.repositories.RoomTypeRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomTypeRepositoryImpl implements RoomTypeRepository{

    @Autowired
    private SessionFactory sessionFactory;

    public List<Roomtype> getRoomTypes() {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Roomtype", Roomtype.class);
        return query.getResultList();

    }
}
