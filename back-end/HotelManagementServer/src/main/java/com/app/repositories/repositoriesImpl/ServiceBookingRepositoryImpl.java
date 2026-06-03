package com.app.repositories.repositoriesImpl;

import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingservice;
import com.app.pojo.Service;
import com.app.repositories.ServiceBookingRepository;
import jakarta.persistence.LockModeType;
import org.hibernate.LockMode;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceBookingRepositoryImpl implements ServiceBookingRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Roombooking getActiveBookingByUserName(String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Roombooking> query = session.createQuery(
                "select distinct b from Roombooking b " +
                "join fetch b.roomBookingStatusId status " +
                "left join fetch b.roombookingdetailList details " +
                "where lower(b.userName) = lower(:userName) " +
                "and lower(status.name) = 'checkin'", 
                Roombooking.class);
        query.setParameter("userName", userName.trim());
        
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        
        List<Roombooking> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Service getServiceById(Integer serviceId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Service.class, serviceId, LockMode.PESSIMISTIC_WRITE);
    }

    @Override
    public void saveBookingService(Roombookingservice roomBookingService) {
        sessionFactory.getCurrentSession().persist(roomBookingService);
    }
}