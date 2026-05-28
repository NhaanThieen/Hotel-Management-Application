package com.app.services.servicesImpl;

import com.app.pojo.Roomtype;
import com.app.repositories.RoomTypeRepository;
import com.app.services.RoomTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public List<Roomtype> getRoomTypes() {
        return this.roomTypeRepository.getRoomTypes();

    }
}
