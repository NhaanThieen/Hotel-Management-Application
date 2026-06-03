package com.app.repositories.repositoriesImpl;

import com.app.pojo.Feedback;
import com.app.repositories.FeedbackRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveFeedback(Feedback feedback) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(feedback);
    }
    
    @Override
    public boolean isFeedbackExist(Integer bookingId) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT COUNT(f) FROM Feedback f WHERE f.roomBookingId.roomBookingId = :bookingId");
        query.setParameter("bookingId", bookingId);
        
        Long count = (Long) query.uniqueResult();
        return count != null && count > 0; 
    }
}