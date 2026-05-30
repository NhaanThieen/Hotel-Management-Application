
package com.app.repositories.repositoriesImpl;

import com.app.pojo.Bedtype;
import com.app.repositories.BedTypeRepository;
import java.util.List;
import java.util.Set;
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
        Query<Bedtype> query = session.createQuery("FROM Bedtype", Bedtype.class);
        return query.getResultList();
      
    }
    
    public List<Bedtype> getBedTypesByListId(Set<Integer> ids){
        Session session = sessionFactory.getCurrentSession();
        Query<Bedtype> query = session.createQuery("FROM Bedtype b WHERE b.bedTypeId IN (:ids)", Bedtype.class);
        query.setParameterList("ids", ids);
        return query.getResultList();
    }
}
