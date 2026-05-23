USE hotel_management_db;

-- ============================================================================
-- 1. INSERT ROLE
-- ============================================================================
INSERT INTO Role (name) VALUES 
('ADMIN'),
('RECEPTIONIST'),
('CUSTOMER');

-- ============================================================================
-- 2. INSERT MEMBER TIER (Chỉ lưu các hạng có ưu đãi tích lũy)
-- ============================================================================
INSERT INTO MemberTier (type, memberDiscountPercent) VALUES 
('Đồng', 5.00),
('Bạc', 10.00),
('Vàng', 15.00);

-- ============================================================================
-- 3. INSERT USER (Đúng 7 tài khoản theo cấu hình nhân sự của bạn)
-- Mật khẩu mặc định đặt tạm là '123456' để bạn dễ test login
-- ============================================================================
INSERT INTO User (name, username, password, phone, isDeleted, roleId, avatar, memberTierId) VALUES 
-- 1 Tài khoản Admin cao cấp nhất
('Nguyễn Quản Trị', 'admin', '123456', '0901234567', 0, 1, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/admin.png', NULL),

-- 2 Tài khoản Nhân viên Lễ tân làm việc tại quầy
('Trần Lễ Tân 1', 'letan1', '123456', '0907654321', 0, 2, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/letan1.png', NULL),
('Lê Lễ Tân 2', 'letan2', '123456', '0912345678', 0, 2, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/letan2.png', NULL),

-- 4 Tài khoản Khách hàng (3 khách có hạng thẻ tăng dần, 1 khách thường không có thẻ)
('Phạm Khách Đồng', 'khachdong', '123456', '0981112222', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user1.png', 1),
('Hoàng Khách Bạc', 'khachbac', '123456', '0983334444', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user2.png', 2),
('Vũ Khách Vàng', 'khachvang', '123456', '0985556666', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user3.png', 3),
('Đỗ Khách Thường', 'khachthuong', '123456', '0987778888', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user4.png', NULL);

-- ============================================================================
-- 4. INSERT ROOM STATUS & ROOM TYPE
-- ============================================================================
INSERT INTO RoomStatus (name) VALUES 
('Available'),
('Occupied'),
('Maintenance');

INSERT INTO RoomType (name, price) VALUES 
('Normal', 500000.00),
('Vip', 1200000.00);

-- ============================================================================
-- 5. INSERT ROOM (20 phòng phân phối đều để kiểm thử thuật toán)
-- ============================================================================
INSERT INTO Room (roomName, isDeleted, version, avatar, roomTypeId, roomStatusId) VALUES 
-- Tầng 1: 10 phòng dòng Normal (Giá 500.000đ)
('Phòng 101', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 102', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 103', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 104', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 105', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 2), -- Occupied
('Phòng 106', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 2), -- Occupied
('Phòng 107', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 3), -- Maintenance
('Phòng 108', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 109', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 110', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),

-- Tầng 2: 10 phòng dòng VIP (Giá 1.200.000đ)
('Phòng 201', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 202', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 203', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 2), -- Occupied
('Phòng 204', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 2), -- Occupied
('Phòng 205', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 206', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 207', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 3), -- Maintenance
('Phòng 208', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 209', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 210', 0, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1);

-- ============================================================================
-- 6. INSERT SERVICE TYPE & SERVICE (10 dịch vụ đa dạng)
-- ============================================================================
INSERT INTO ServiceType (name) VALUES 
('F&B (Ẩm thực)'),
('Thư giãn & Tiện ích'),
('Di chuyển');

INSERT INTO Service (name, price, stock, isDeleted, serviceTypeId) VALUES 
-- Ẩm thực tại phòng
('Mì Ý sốt bò băm', 85000.00, 50, 0, 1),
('Bò bít tết khoai tây', 180000.00, 30, 0, 1),
('Nước ngọt Coca-Cola', 20000.00, 200, 0, 1),
('Bia Heineken', 35000.00, 150, 0, 1),

-- Dịch vụ tiện ích gia tăng
('Massage Thảo Dược (60 phút)', 300000.00, 10, 0, 2),
('Giặt ủi cấp tốc (theo kg)', 40000.00, 100, 0, 2),
('Xông hơi đá muối', 150000.00, 15, 0, 2),

-- Dịch vụ phương tiện di chuyển
('Thuê xe máy tay ga (đầy bình)', 150000.00, 12, 0, 3),
('Thuê xe máy số (đầy bình)', 120000.00, 8, 0, 3),
('Đưa/Đón sân bay bằng ô tô', 250000.00, 5, 0, 3);

-- ============================================================================
-- 7. INSERT VOUCHER (10 mẫu khuyến mãi có sàn áp dụng khác nhau)
-- ============================================================================
INSERT INTO Voucher (name, quantity, percentDiscount, maxDiscount, minRequire, isActive, isDeleted) VALUES 
('CHÀO MỪNG HÈ - GIẢM 10%', 10, 10.00, 100000.00, 0.00, 1, 0),
('KHÁCH HÀNG MỚI - THUÊ PHÒNG GIẢM 5%', 10, 5.00, 50000.00, 0.00, 1, 0),
('TRI ÂN THÀNH VIÊN VIP - GIẢM 20%', 10, 20.00, 300000.00, 1000000.00, 1, 0),
('ĐƠN TO GIẢM ĐẬM - GIẢM 15%', 10, 15.00, 500000.00, 2000000.00, 1, 0),
('ƯU ĐÃI ĐẶT SỚM - GIẢM 8%', 10, 8.00, 80000.00, 500000.00, 1, 0),
('GIẢM TRỰC TIẾP 50K', 10, 0.00, 50000.00, 300000.00, 1, 0),
('GIẢM TRỰC TIẾP 100K', 10, 0.00, 100000.00, 800000.00, 1, 0),
('GIẢM TRỰC TIẾP 200K', 10, 0.00, 200000.00, 1500000.00, 1, 0),
('KHUYẾN MÃI CUỐI TUẦN - GIẢM 12%', 10, 12.00, 200000.00, 600000.00, 1, 0),
('VOUCHER ĐẶT PHÒNG VIP - GIẢM 18%', 10, 18.00, 400000.00, 1200000.00, 1, 0);

-- ============================================================================
-- 8. INSERT PAYMENT METHOD & ROOM BOOKING STATUS
-- ============================================================================
INSERT INTO PaymentMethod (name) VALUES 
('Tiền mặt'),
('Chuyển khoản ngân hàng');

INSERT INTO RoomBookingStatus (name) VALUES 
('Pending'),
('Cancel'),
('Timeout'),
('Paid');

-- ============================================================================
-- 9. INSERT MANUAL DISCOUNT REASON (5 lý do đền bù sự cố thực tế)
-- ============================================================================
INSERT INTO ManualDiscountReason (name) VALUES 
('Sự cố cơ sở vật chất (Phòng bị dột nước, hỏng điều hòa)'),
('Nhân viên quầy lễ tân phục vụ chậm trễ khiến khách phiền lòng'),
('Khách hàng khiếu nại về tiếng ồn từ phòng kế bên'),
('Chương trình ưu đãi nội bộ do Ban Giám Đốc phê duyệt trực tiếp'),
('Giảm giá đặc quyền đền bù lỗi hệ thống đặt phòng trực tuyến');