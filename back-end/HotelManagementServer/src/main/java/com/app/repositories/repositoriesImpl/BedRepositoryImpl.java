/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Bed;
import com.app.repositories.BedRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BedRepositoryImpl implements BedRepository{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Bed> saveAll(List<Bed> beds){
        Session session = sessionFactory.getCurrentSession();
        for(var b : beds){
            session.persist(b);
        }
        return beds;
    }
}
