package com.app.services.servicesImpl;

import com.app.dto.request.ServiceBookingRequest;
import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingdetail;
import com.app.pojo.Roombookingservice;
import com.app.repositories.ServiceBookingRepository;
import com.app.services.ServiceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ServiceBookingServiceImpl implements ServiceBookingService {

    @Autowired
    private ServiceBookingRepository serviceBookingRepository;

    @Override
    @Transactional
    public Integer addServiceToBooking(ServiceBookingRequest request) {
        Roombooking activeBooking = serviceBookingRepository.getActiveBookingByUserName(request.getUserName());
        if (activeBooking == null) {
            throw new IllegalStateException("Khách hàng phải đang nhận phòng (Check-in) mới được đặt thêm dịch vụ.");
        }

        // 2. Lấy thông tin dịch vụ (Đã bị khóa PESSIMISTIC_WRITE ở Repo)
        com.app.pojo.Service service = serviceBookingRepository.getServiceById(request.getServiceId());
        if (service == null || (service.getIsDeleted() != null && service.getIsDeleted() == 1)) {
            throw new NoSuchElementException("Dịch vụ không tồn tại hoặc đã ngừng kinh doanh.");
        }

        int qtyToBuy = (request.getQuantity() != null && request.getQuantity() > 0) ? request.getQuantity() : 1;


        if (service.getQuantity() != null) {
            if (service.getQuantity() < qtyToBuy) {
                throw new IllegalStateException("Rất tiếc, dịch vụ này số lượng không đủ hoặc đã hết hàng.");
            }
            service.setQuantity(service.getQuantity() - qtyToBuy);
        }

        if (activeBooking.getRoombookingdetailList() == null || activeBooking.getRoombookingdetailList().isEmpty()) {
            throw new IllegalStateException("Lỗi dữ liệu: Đơn đặt phòng không có chi tiết phòng.");
        }
        Roombookingdetail detail = activeBooking.getRoombookingdetailList().get(0);

        Roombookingservice newServiceRecord = new Roombookingservice();
        newServiceRecord.setRoomBookingDetailId(detail);
        newServiceRecord.setServiceId(service);
        newServiceRecord.setQuantity(qtyToBuy);
        newServiceRecord.setUnitServicePrice(service.getPrice()); 
        newServiceRecord.setCreateAt(new java.util.Date());

        serviceBookingRepository.saveBookingService(newServiceRecord);
        
        return activeBooking.getRoomBookingId();
    }
}