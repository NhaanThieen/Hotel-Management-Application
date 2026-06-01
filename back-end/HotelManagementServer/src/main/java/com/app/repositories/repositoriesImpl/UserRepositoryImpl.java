/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.User;
import com.app.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE username=:username", User.class);
        query.setParameter("username", username);
        return query.getSingleResultOrNull();
    }

    @Override
    public User createUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE email=:email", User.class);
        query.setParameter("email", email);
        return query.getSingleResultOrNull();
    }

    @Override
    public User getUserByPhone(String phone) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE phone=:phone", User.class);
        query.setParameter("phone", phone);
        return query.getSingleResultOrNull();
    }
}
