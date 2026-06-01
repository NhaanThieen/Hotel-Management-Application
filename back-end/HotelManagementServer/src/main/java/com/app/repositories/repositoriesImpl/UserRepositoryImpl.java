/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.repositories.repositoriesImpl;

import com.app.dto.request.UserSearchCriteria;
import com.app.pojo.User;
import com.app.repositories.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Environment env;

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

    @Override
    public List<User> getUsers(UserSearchCriteria criteria) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        // Lọc theo username
        if (criteria.getUsername() != null && !criteria.getUsername().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("username")), String.format("%%%s%%", criteria.getUsername()).toLowerCase()));
        }

        // Lọc theo Role
        if (criteria.getRoleId() != null) {
            predicates.add(cb.equal(root.get("roleId").get("roleId"), criteria.getRoleId()));
        }

        // Lọc theo trạng thái
        if (criteria.getIsDeleted() != null) {
            predicates.add(cb.equal(root.get("isDeleted"), criteria.getIsDeleted()));
        }

        // Thêm predicate vào where
        cq.where(predicates.toArray(Predicate[]::new));

        cq.select(root);

        // Sắp xếp từ Z -> A
        cq.orderBy(cb.desc(root.get("userId")));

        Query query = session.createQuery(cq);

        // Phân trang
        int page = criteria.getPage();
        int pageSize = Integer.parseInt(env.getProperty("user.page_size"));
        int start = (page - 1) * pageSize;

        query.setMaxResults(pageSize);
        query.setFirstResult(start);
        
        return query.getResultList();
    }
}
