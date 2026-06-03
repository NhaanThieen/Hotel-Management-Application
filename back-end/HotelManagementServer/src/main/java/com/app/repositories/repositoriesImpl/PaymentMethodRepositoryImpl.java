/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Paymentmethod;
import com.app.repositories.PaymentMethodRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Paymentmethod getPaymentMethodByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Paymentmethod> query = session.createQuery("FROM Paymentmethod WHERE LOWER(name) = LOWER(:name)", Paymentmethod.class);
        query.setParameter("name", name);
        return query.getSingleResultOrNull();
    }
}
