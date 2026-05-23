
package com.app.repositories.repositoriesImpl;

import com.app.pojo.User;
import com.app.repositories.UserRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public List<User> getUsers() {
           Session session = this.sessionFactory.getCurrentSession();
           Query query = session.createQuery("FROM User", User.class);
          return query.getResultList();
    }
    
    
}
