/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Role;
import com.app.repositories.UserRoleRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository{
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public List<Role> getUserRoles(){
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery("FROM Role", Role.class);
        return query.getResultList();
    }
}
