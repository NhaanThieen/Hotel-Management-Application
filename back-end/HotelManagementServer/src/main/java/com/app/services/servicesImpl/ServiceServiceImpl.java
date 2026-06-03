package com.app.services.servicesImpl;

import com.app.dto.response.ApiServiceDTO;
import com.app.repositories.ServiceRepository;
import com.app.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<ApiServiceDTO> getActiveServicesForClient() {
        List<com.app.pojo.Service> entities = serviceRepository.getActiveServices();
        List<ApiServiceDTO> dtos = new ArrayList<>();
        
        for (com.app.pojo.Service entity : entities) {
            ApiServiceDTO dto = new ApiServiceDTO();
            dto.setServiceId(entity.getServiceId());
            dto.setName(entity.getName());
            dto.setPrice(entity.getPrice());
            dto.setIsDeleted(entity.getIsDeleted() != null ? (int) entity.getIsDeleted() : 0);

            String typeName = "Nổi bật"; 
            String defaultImg = "https://images.unsplash.com/photo-1542314831-c53cd3816002?q=80&w=600"; 
            
            if (entity.getServiceTypeId() != null) {
                int typeId = entity.getServiceTypeId().getServiceTypeId(); 
                
                switch (typeId) {
                    case 1:
                        typeName = "F&B (Ẩm thực)";
                        defaultImg = "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?q=80&w=600"; 
                        break;
                    case 2:
                        typeName = "Thư giãn & Tiện ích";
                        defaultImg = "https://images.unsplash.com/photo-1540555700478-4be289fbecef?q=80&w=600"; 
                        break;
                    case 3:
                        typeName = "Di chuyển";
                        defaultImg = "https://images.unsplash.com/photo-1449965408869-eaa3f722e40d?q=80&w=600"; 
                        break;
                }
            }
            
            dto.setType(typeName);
            dto.setImgURL(defaultImg);

            dtos.add(dto);
        }
        
        return dtos;
    }
}