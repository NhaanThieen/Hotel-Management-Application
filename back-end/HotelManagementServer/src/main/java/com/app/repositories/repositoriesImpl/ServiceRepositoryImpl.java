package com.app.repositories.repositoriesImpl;

import com.app.pojo.Service;
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
    public Service getServiceById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service WHERE serviceId = :id", Service.class);
        query.setParameter("id", id);
        return query.getSingleResultOrNull();
    }

    @Override
    public Integer deductStock(Integer serviceId, Integer quantity) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE Service s SET s.stock = s.stock - :qty WHERE s.serviceId = :id AND s.stock >= :qty";
        Query<Object> query = session.createQuery(hql, Object.class);
        query.setParameter("id", serviceId);
        query.setParameter("qty", quantity);
        
        return query.executeUpdate();
    }

    @Override
    public List<Service> getActiveServices() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Service> cq = cb.createQuery(Service.class);
        Root<Service> root = cq.from(Service.class);

        cq.where(cb.equal(root.get("isDeleted"), 0));
        cq.select(root);
        
        cq.orderBy(cb.desc(root.get("serviceId")));

        Query<Service> query = session.createQuery(cq);
        return query.getResultList();
    }
}