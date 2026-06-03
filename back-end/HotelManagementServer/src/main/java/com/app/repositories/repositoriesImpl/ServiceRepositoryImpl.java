package com.app.repositories.repositoriesImpl;

import com.app.repositories.ServiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<com.app.pojo.Service> getActiveServices() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<com.app.pojo.Service> cq = cb.createQuery(com.app.pojo.Service.class);
        Root<com.app.pojo.Service> root = cq.from(com.app.pojo.Service.class);

        cq.where(cb.equal(root.get("isDeleted"), 0));
        cq.select(root);
        
        cq.orderBy(cb.desc(root.get("serviceId")));

        Query<com.app.pojo.Service> query = session.createQuery(cq);
        return query.getResultList();
    }
}