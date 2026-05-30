
package com.app.services.servicesImpl;

import com.app.pojo.Bedtype;
import com.app.repositories.BedTypeRepository;
import com.app.services.BedTypeService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class BedTypeServiceImp implements BedTypeService{
    
    @Autowired
    private BedTypeRepository bedTypeRepository;
    
    public List<Bedtype> getBedTypes(){
        return this.bedTypeRepository.getBedTypes();
    }
    public List<Bedtype> getBedTypesByListId(Set<Integer> ids){
        return this.bedTypeRepository.getBedTypesByListId(ids);
    }
}
