
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Bedtype;
import com.app.repositories.BedTypeRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class BedTypeRepositoryImpl implements BedTypeRepository{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Bedtype> getBedTypes(){
    
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Bedtype", Bedtype.class);
        return query.getResultList();
      
    }
}
