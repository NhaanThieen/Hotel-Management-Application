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
-- 3. INSERT USER (Đúng 7 tài khoản theo cấu hình nhân sự)
-- Mật khẩu mặc định đặt tạm là '123456' để test login
-- ============================================================================
INSERT INTO User (name, username, password, phone, isDeleted, roleId, avatar, memberTierId) VALUES 
('Nguyễn Quản Trị', 'admin', '123456', '0901234567', 0, 1, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/admin.png', NULL),
('Trần Lễ Tân 1', 'letan1', '123456', '0907654321', 0, 2, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/letan1.png', NULL),
('Lê Lễ Tân 2', 'letan2', '123456', '0912345678', 0, 2, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/letan2.png', NULL),
('Phạm Khách Đồng', 'khachdong', '123456', '0981112222', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user1.png', 1),
('Hoàng Khách Bạc', 'khachbac', '123456', '0983334444', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user2.png', 2),
('Vũ Khách Vàng', 'khachvang', '123456', '0985556666', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user3.png', 3),
('Đỗ Khách Thường', 'khachthuong', '123456', '0987778888', 0, 3, 'https://res.cloudinary.com/demo/image/upload/v1/avatar/user4.png', NULL);

-- ============================================================================
-- 4. INSERT ROOM STATUS, ROOM TYPE & BED TYPE
-- ============================================================================
INSERT INTO RoomStatus (name) VALUES 
('Available'),
('Occupied'),
('Maintenance');

-- Đã sửa: Có price và thêm description theo schema
INSERT INTO RoomType (name, price, description) VALUES 
('Normal', 500000.00, 'Phòng tiêu chuẩn cơ bản'),
('Vip', 1200000.00, 'Phòng cao cấp, không gian rộng');

-- Bổ sung bảng BedType theo schema
INSERT INTO BedType (name) VALUES 
('Giường Đơn (Single)'),
('Giường Đôi (Double)');

-- ============================================================================
-- 5. INSERT ROOM (Thêm cột price, capacity, đổi avatar thành thumbnail)
-- ============================================================================
INSERT INTO Room (roomName, isDeleted, price, capacity, version, thumbnail, roomTypeId, roomStatusId) VALUES 
-- Tầng 1: 10 phòng dòng Normal (Giá 500k, sức chứa 2)
('Phòng 101', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 102', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 103', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 104', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 105', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 2),
('Phòng 106', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 2),
('Phòng 107', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 3),
('Phòng 108', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 109', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),
('Phòng 110', 0, 500000.00, 2, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/normal.png', 1, 1),

-- Tầng 2: 10 phòng dòng VIP (Giá 1tr2, sức chứa 4)
('Phòng 201', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 202', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 203', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 2),
('Phòng 204', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 2),
('Phòng 205', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 206', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 207', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 3),
('Phòng 208', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 209', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1),
('Phòng 210', 0, 1200000.00, 4, 0, 'https://res.cloudinary.com/demo/image/upload/v1/rooms/vip.png', 2, 1);

-- ============================================================================
-- 6. BỔ SUNG CẤU HÌNH BED VÀ ROOM IMAGE (Theo đúng Schema mới)
-- ============================================================================
-- Bảng Bed: (amount, bedTypeId, roomId)
-- 10 Phòng Normal (Id 1-10) có 1 giường đôi (bedTypeId = 2)
INSERT INTO Bed (amount, bedTypeId, roomId) VALUES 
(1, 2, 1), (1, 2, 2), (1, 2, 3), (1, 2, 4), (1, 2, 5),
(1, 2, 6), (1, 2, 7), (1, 2, 8), (1, 2, 9), (1, 2, 10);

-- 10 Phòng VIP (Id 11-20) có 1 giường đôi (2) và 1 giường đơn (1)
INSERT INTO Bed (amount, bedTypeId, roomId) VALUES 
(1, 2, 11), (1, 1, 11), (1, 2, 12), (1, 1, 12), (1, 2, 13), (1, 1, 13),
(1, 2, 14), (1, 1, 14), (1, 2, 15), (1, 1, 15), (1, 2, 16), (1, 1, 16),
(1, 2, 17), (1, 1, 17), (1, 2, 18), (1, 1, 18), (1, 2, 19), (1, 1, 19),
(1, 2, 20), (1, 1, 20);

-- Bảng RoomImage: Ảnh góc chụp phụ cho phòng (url, roomId)
INSERT INTO RoomImage (url, roomId) VALUES
('https://res.cloudinary.com/demo/image/upload/v1/rooms/101-view1.png', 1),
('https://res.cloudinary.com/demo/image/upload/v1/rooms/101-view2.png', 1),
('https://res.cloudinary.com/demo/image/upload/v1/rooms/201-view1.png', 11),
('https://res.cloudinary.com/demo/image/upload/v1/rooms/201-view2.png', 11);

-- ============================================================================
-- 7. INSERT SERVICE TYPE & SERVICE (10 dịch vụ đa dạng)
-- ============================================================================
INSERT INTO ServiceType (name) VALUES 
('F&B (Ẩm thực)'),
('Thư giãn & Tiện ích'),
('Di chuyển');

INSERT INTO Service (name, price, stock, isDeleted, serviceTypeId) VALUES 
('Mì Ý sốt bò băm', 85000.00, 50, 0, 1),
('Bò bít tết khoai tây', 180000.00, 30, 0, 1),
('Nước ngọt Coca-Cola', 20000.00, 200, 0, 1),
('Bia Heineken', 35000.00, 150, 0, 1),
('Massage Thảo Dược (60 phút)', 300000.00, 10, 0, 2),
('Giặt ủi cấp tốc (theo kg)', 40000.00, 100, 0, 2),
('Xông hơi đá muối', 150000.00, 15, 0, 2),
('Thuê xe máy tay ga (đầy bình)', 150000.00, 12, 0, 3),
('Thuê xe máy số (đầy bình)', 120000.00, 8, 0, 3),
('Đưa/Đón sân bay bằng ô tô', 250000.00, 5, 0, 3);

-- ============================================================================
-- 8. INSERT VOUCHER (10 mẫu khuyến mãi)
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
-- 9. INSERT PAYMENT METHOD & ROOM BOOKING STATUS
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
-- 10. INSERT MANUAL DISCOUNT REASON (5 lý do đền bù sự cố thực tế)
-- ============================================================================
INSERT INTO ManualDiscountReason (name) VALUES 
('Sự cố cơ sở vật chất (Phòng bị dột nước, hỏng điều hòa)'),
('Nhân viên quầy lễ tân phục vụ chậm trễ khiến khách phiền lòng'),
('Khách hàng khiếu nại về tiếng ồn từ phòng kế bên'),
('Chương trình ưu đãi nội bộ do Ban Giám Đốc phê duyệt trực tiếp'),
('Giảm giá đặc quyền đền bù lỗi hệ thống đặt phòng trực tuyến');