/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roomimage;
import com.app.repositories.RoomImageRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RoomImageRepositoryImpl implements RoomImageRepository{
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Roomimage> saveAll(List<Roomimage> roomImages) {
        Session session = sessionFactory.getCurrentSession();
        for(var r : roomImages){
            session.persist(r);
        }
        return roomImages;
    }
}
