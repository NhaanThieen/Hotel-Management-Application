package com.app.repositories.repositoriesImpl;

import com.app.dto.request.ApiRoomSearchCriteria;
import com.app.dto.request.RoomSearchCriteria;
import com.app.pojo.Bed;
import com.app.pojo.Room;
import com.app.pojo.Roombooking;
import com.app.pojo.Roombookingdetail;
import com.app.repositories.RoomRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
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

        // Sắp xếp từ Z -> A
        cq.orderBy(cb.desc(root.get("roomId")));

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
            Hibernate.initialize(room.getBedList());

            for (Bed bed : room.getBedList()) {
                Hibernate.initialize(bed.getBedTypeId());
            }
        }
        return rooms;
    }

    @Override
    public List<Room> getRoomsForClient(ApiRoomSearchCriteria roomData) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);

        List<Predicate> predicates = new ArrayList<>();

        root.fetch("roomTypeId");

        // Tạo subquery để lấy list room bận trong khoảng thời gian
        if (roomData.getCheckIn() != null && roomData.getCheckOut() != null) {
            List<Predicate> subPredicates = new ArrayList<>();
            Subquery<Integer> sq = cq.subquery(Integer.class);
            Root<Roombookingdetail> rbdRoot = sq.from(Roombookingdetail.class);
            // Join bảng
            Join<Roombookingdetail, Roombooking> rbJoin = rbdRoot.join("roomBookingId");
            // 2 điều kiện phát hiện trùng 
            Predicate trungA = cb.greaterThan(rbJoin.get("bookingCheckOut"), roomData.getCheckIn());
            Predicate trungB = cb.lessThan(rbJoin.get("bookingCheckIn"), roomData.getCheckOut());
            subPredicates.add(trungA);
            subPredicates.add(trungB);
            // Loại những booking đã hủy
            Predicate huy = cb.notEqual(rbJoin.get("roomBookingStatusId").get("name"), "Cancel");
            subPredicates.add(huy);

            // Lấy roomId của đối tượng roomId
            sq.select(rbdRoot.get("roomId").get("roomId"))
                    .where(subPredicates.toArray(Predicate[]::new));

            // Thêm vào predicate chính là không lấy những room trong đây
            predicates.add(cb.not(root.get("roomId").in(sq)));
        }

        // Chỉ hiển thị khi chưa bị soft delete
        predicates.add(cb.equal(root.get("isDeleted"), (short) 0));

        // Lọc theo loại phòng
        if (roomData.getRoomTypeId() != null) {
            predicates.add(cb.equal(root.get("roomTypeId").get("roomTypeId"), roomData.getRoomTypeId()));
        }

        // Lọc theo giá của phòng
        if (roomData.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), roomData.getMinPrice()));
        }

        if (roomData.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), roomData.getMaxPrice()));
        }

        // Lọc theo roombooking Date
        // Thêm predicate vào where
        cq.where(predicates.toArray(Predicate[]::new));

        cq.select(root);

        // Sắp xếp từ Z -> A
        cq.orderBy(cb.desc(root.get("roomId")));

        Query query = session.createQuery(cq);

        // Phân trang
        int page = roomData.getPage();
        int pageSize = Integer.parseInt(env.getProperty("room.page_size.client"));
        int start = (page - 1) * pageSize;

        query.setMaxResults(pageSize);
        query.setFirstResult(start);

        List<Room> rooms = query.getResultList();

        // List bed là lazy, nên ép hibernate lấy danh sách list bed nạp vào ram luôn. 
        // do OSIV đã tắt, session đã đóng nên view không thể truy vấn dữ liệu được nữa.
        for (Room room : rooms) {
            Hibernate.initialize(room.getBedList());

            for (Bed bed : room.getBedList()) {
                Hibernate.initialize(bed.getBedTypeId());
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

    @Override
    public Room getBasicRoomById(Integer roomId) {
        Session session = sessionFactory.getCurrentSession();
        // Sử dụng + để sau này muốn join thêm bảng nào thì ghi vào

        // Chỉ get những trường EAGER ManytoOne
        String hql = "SELECT r FROM Room r "
                + "LEFT JOIN FETCH r.roomStatusId "
                + "LEFT JOIN FETCH r.roomTypeId "
                + "WHERE r.roomId = :roomId AND r.isDeleted = 0";

        Query<Room> query = session.createQuery(hql, Room.class);
        query.setParameter("roomId", roomId);

        return query.getSingleResultOrNull();
    }

    @Override
    public Boolean isRoomBusy(Integer roomId, Date checkIn, Date checkOut) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Dùng long để count do hibernate bắt buộc
        CriteriaQuery<Roombookingdetail> cq = cb.createQuery(Roombookingdetail.class);
        Root<Roombookingdetail> rbdRoot = cq.from(Roombookingdetail.class);

        Join<Roombookingdetail, Roombooking> rbJoin = rbdRoot.join("roomBookingId");

        Predicate roomCondition = cb.equal(rbdRoot.get("roomId").get("roomId"), roomId);
        Predicate trungA = cb.greaterThan(rbJoin.get("bookingCheckOut"), checkIn);
        Predicate trungB = cb.lessThan(rbJoin.get("bookingCheckIn"), checkOut);
        Predicate huy = cb.notEqual(rbJoin.get("roomBookingStatusId").get("name"), "Cancel");

        cq.select(rbdRoot)
                .where(cb.and(roomCondition, trungA, trungB, huy));

        long count = session.createQuery(cq).getResultCount();
        return count > 0;
    }

    @Override
    public void lockRoom(Room room) {
        Session session = sessionFactory.getCurrentSession();

        session.lock(room, LockMode.OPTIMISTIC_FORCE_INCREMENT);
    }

    @Override
    public List<Room> getRoomsForReceptionist() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT r FROM Room r JOIN FETCH r.roomStatusId WHERE r.isDeleted = 0 OR r.isDeleted IS NULL ORDER BY r.roomName ASC";
        Query<Room> query = session.createQuery(hql, Room.class);
        return query.getResultList();
    }
    
}
