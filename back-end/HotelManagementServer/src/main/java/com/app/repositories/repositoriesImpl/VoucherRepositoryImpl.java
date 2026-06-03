/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Voucher;
import com.app.repositories.VoucherRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Voucher getVoucherById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Voucher> query = session.createQuery("FROM Voucher WHERE voucherId = :id", Voucher.class);
        query.setParameter("id", id);
        return query.getSingleResultOrNull();
    }

}
