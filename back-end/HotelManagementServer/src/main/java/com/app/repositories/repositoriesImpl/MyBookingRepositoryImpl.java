package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingstatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.app.repositories.MyBookingRepository;

@Repository
public class MyBookingRepositoryImpl implements MyBookingRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Roombooking> getBookingsByUserName(String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Roombooking> query = session.createQuery("FROM Roombooking r WHERE r.userId.username = :userName ORDER BY r.roomBookingId DESC", Roombooking.class);
        query.setParameter("userName", userName);
        return query.getResultList();
    }

    @Override
    public Roombooking getBookingByIdAndUserName(Integer bookingId, String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Roombooking> query = session.createQuery("FROM Roombooking r WHERE r.roomBookingId = :id AND r.userId.username = :userName", Roombooking.class);
        query.setParameter("id", bookingId);
        query.setParameter("userName", userName);
        return query.uniqueResult();
    }

    @Override
    public Roombookingstatus getBookingStatusByName(String statusName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Roombookingstatus> query = session.createQuery("FROM Roombookingstatus r WHERE r.name = :name", Roombookingstatus.class);
        query.setParameter("name", statusName);
        return query.uniqueResult();
    }
}