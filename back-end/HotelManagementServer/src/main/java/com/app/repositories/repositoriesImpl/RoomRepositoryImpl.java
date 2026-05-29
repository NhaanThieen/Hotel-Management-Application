package com.app.repositories.repositoriesImpl;

import com.app.dto.request.RoomSearchCriteria;
import com.app.pojo.Bed;
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
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);
        
        // Mặc dù RoomType và RoomStatus là eager, nhưng HQL chỉ lấy room, không tự động lấy 2 thuộc tính này
        // hibernate sẽ chữa cháy bằng cách gọi bù câu sql => lỗi N + 1 Query.
        // => Tường minh ép hibernate lấy room và 2 thuộc tính cùng 1 lúc luôn.
        root.fetch("roomTypeId");
        root.fetch("roomStatusId");
        
        List<Predicate> predicates = new ArrayList<>();

        // Chỉ hiển thị khi chưa bị soft delete
        predicates.add(cb.equal(root.get("isDeleted"), (short) 0));

        // Lọc theo tên phòng
        if (roomData.getName() != null && !roomData.getName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("roomName")), String.format("%%%s%%", roomData.getName()).toLowerCase()));
        }

        // Lọc theo loại phòng
        if (roomData.getTypeId() != null) {
            predicates.add(cb.equal(root.get("roomTypeId").get("roomTypeId"), roomData.getTypeId()));
        }

        // Lọc theo trạng thái phòng
        if (roomData.getStatusId() != null) {
            predicates.add(cb.equal(root.get("roomStatusId").get("roomStatusId"), roomData.getStatusId()));
        }

        // Lọc theo giá của phòng
        if (roomData.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), roomData.getMinPrice()));
        }

        if (roomData.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), roomData.getMaxPrice()));
        }

        // Thêm predicate vào where
        cq.where(predicates.toArray(Predicate[]::new));


        cq.select(root);

        Query query = session.createQuery(cq);

        // Phân trang
        int page = roomData.getPage();
        int pageSize = Integer.parseInt(env.getProperty("room.page_size"));
        int start = (page - 1) * pageSize;

        query.setMaxResults(pageSize);
        query.setFirstResult(start);

        List<Room> rooms = query.getResultList();

        // List bed là lazy, nên ép hibernate lấy danh sách list bed nạp vào ram luôn. 
        // do OSIV đã tắt, session đã đóng nên view không thể truy vấn dữ liệu được nữa.
        for (Room room : rooms) {
            org.hibernate.Hibernate.initialize(room.getBedList());

            for (Bed bed : room.getBedList()) {
                org.hibernate.Hibernate.initialize(bed.getBedTypeId());
            }
        }
        return rooms;
    }

    @Override
    public Room saveRoom(Room room) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(room);
        return room;
    }
}
