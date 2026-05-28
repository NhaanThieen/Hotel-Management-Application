package com.app.repositories.repositoriesImpl;

import com.app.dto.request.RoomSearchCriteria;
import com.app.pojo.Room;
import com.app.repositories.RoomRepository;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:database.properties")
public class RoomRepositoryImpl implements RoomRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Environment env;

    @Override
    public List<Room> getRooms(RoomSearchCriteria roomData) {
        // Tự động lấy session đang có trong threadlocal
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root root = cq.from(Room.class);

        List<Predicate> predicates = new ArrayList<>();

        // predicates lọc thêm tên phòng
        if (roomData.getName() != null && !roomData.getName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("roomName")), String.format("%%%s%%", roomData.getName()).toLowerCase()));
        }

        // predicate lọc theo loại phòng
        if (roomData.getTypeId() != null) {
            predicates.add(cb.equal(root.get("roomTypeId").get("roomTypeId"), roomData.getTypeId()));
        }

        // predicate lọc theo trạng thái phòng
        if (roomData.getStatusId() != null) {
            predicates.add(cb.equal(root.get("roomStatusId").get("roomStatusId"), roomData.getStatusId()));
        }

        // predicate lọc theo giá tiền
        if (roomData.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("roomTypeId").get("price"), roomData.getMinPrice()));
        }

        if (roomData.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("roomTypeId").get("price"), roomData.getMaxPrice()));

        }
        
        // Thêm predicate vào where
        cq.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(cq);

        // Phân trang
        int page = roomData.getPage();
        int start = (page - 1) * Integer.parseInt(env.getProperty("room.page_size"));

        query.setMaxResults(Integer.parseInt(env.getProperty("room.page_size")));
        query.setFirstResult(start);

        return query.getResultList();
    }
}
