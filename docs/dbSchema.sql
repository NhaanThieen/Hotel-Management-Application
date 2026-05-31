CREATE DATABASE IF NOT EXISTS hotel_management_db;
USE hotel_management_db;

-- Component USER
CREATE TABLE Role (
    roleId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE MemberTier (
    memberTierId INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    memberDiscountPercent DECIMAL(5,2) DEFAULT 0.00
);

CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(15) UNIQUE,
    isDeleted TINYINT DEFAULT 0,
    roleId INT NOT NULL,
    avatar TEXT NULL,
    memberTierId INT NULL, -- Cho phép NULL nếu là nhân viên hoặc khách vãng lai
    FOREIGN KEY (roleId) REFERENCES Role(roleId),
    FOREIGN KEY (memberTierId) REFERENCES MemberTier(memberTierId)
);

-- Không mock dữ liệu bảng này, provider sử dụng enum trong java.
CREATE TABLE UserSocialAccount (
    UserSocialAccountId INT AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(255) NOT NULL,
    providerId VARCHAR(255) NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES User(userId),
    UNIQUE(provider, providerId)
);

-- Component ROOM
CREATE TABLE RoomStatus (
    roomStatusId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE RoomType (
    roomTypeId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price DECIMAL(15,2) NOT NULL, -- Đổi sang DECIMAL cho chuẩn tiền tệ
    description TEXT
);

CREATE TABLE BedType(
	bedTypeId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Room (
    roomId INT AUTO_INCREMENT PRIMARY KEY,
    roomName VARCHAR(50) NOT NULL,
    isDeleted TINYINT DEFAULT 0,
    price DECIMAL(15,2) NOT NULL,
	capacity INT DEFAULT 1, -- Số lượng người tối đa trong 1 phòng
    version INT DEFAULT 0, -- Kích hoạt Khóa lạc quan cho Hibernate
    thumbnail TEXT NULL,
    roomTypeId INT NOT NULL,
    roomStatusId INT NOT NULL,
    FOREIGN KEY (roomTypeId) REFERENCES RoomType(roomTypeId),
    FOREIGN KEY (roomStatusId) REFERENCES RoomStatus(roomStatusId)
);

CREATE TABLE Bed(
	bedId INT AUTO_INCREMENT PRIMARY KEY,
    amount INT DEFAULT 0,
    bedTypeId INT NOT NULL,
    roomId INT NOT NULL,
	FOREIGN KEY (roomId) REFERENCES Room(roomId),
    FOREIGN KEY (bedTypeId) REFERENCES BedType(bedTypeId),
    UNIQUE (roomId, bedTypeId)
);

CREATE TABLE RoomImage(
	roomImageId INT AUTO_INCREMENT PRIMARY KEY,
    url TEXT,
    roomId INT NOT NULL,
	FOREIGN KEY (roomId) REFERENCES Room(roomId)
);

-- Component SERVICE
CREATE TABLE ServiceType (
    serviceTypeId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(155) NOT NULL
);

CREATE TABLE Service (
    serviceId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(155) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    stock INT NOT NULL, -- Atomic Update
    isDeleted TINYINT DEFAULT 0,
    serviceTypeId INT NOT NULL,
    FOREIGN KEY (serviceTypeId) REFERENCES ServiceType(serviceTypeId)
);

-- Component VOUCHER
CREATE TABLE Voucher (
    voucherId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    percentDiscount DECIMAL(5,2) DEFAULT 0.00,
    maxDiscount DECIMAL(15,2) DEFAULT 0.00,
    minRequire DECIMAL(15,2) DEFAULT 0.00,
    isActive TINYINT DEFAULT 1,
    isDeleted TINYINT DEFAULT 0
);

-- Component Payment
CREATE TABLE PaymentMethod (
    paymentMethodId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Component Receipt
CREATE TABLE Receipt (
    receiptId INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(155) NOT NULL, -- Lưu snapshot
    userPhone VARCHAR(15) NOT NULL, -- lưu snapshot
    userPaidId INT NOT NULL, -- user thanh toán thật sự
	totalPrice DECIMAL(15,2) NOT NULL, -- Không dùng formula do formula là cột ảo trên Ram
    timeCheckOut DATETIME NOT NULL,
    memberDiscountAmount DECIMAL(15,2) DEFAULT 0.00,
    manualDiscountAmount DECIMAL(15,2) DEFAULT 0.00, -- Giảm giá do ngoại cảnh.
    staffId INT NOT NULL, -- lưu snapshot nhân viên thực hiện
    staffName VARCHAR(155) NOT NULL, -- lưu snapshot nhân viên thực hiện
    paymentMethodId INT NOT NULL,
    FOREIGN KEY (userPaidId) REFERENCES User(userId),
	FOREIGN KEY (staffId) REFERENCES User(userId),
    FOREIGN KEY (paymentMethodId) REFERENCES PaymentMethod(paymentMethodId)
);

-- Component ROOMBOOKING
CREATE TABLE RoomBookingStatus (
	-- Trạng thái bao gồm Pending, Paid, Cancelled và TimeOut
    roomBookingStatusId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(155) NOT NULL
);

CREATE TABLE RoomBooking (
    roomBookingId INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(155) NOT NULL, -- lưu snapshot tên khách
    bookingCheckIn DATETIME NOT NULL,
    bookingCheckOut DATETIME NOT NULL,
	timeCheckIn DATETIME NULL,
    expiredTime DATETIME NOT NULL, -- Nếu sau thời gian này mà khách không tới thì hủy roombooking này
    voucherDiscountMoney DECIMAL(15,2) DEFAULT 0.00,
    depositAmount DECIMAL(15,2) DEFAULT 0.00, -- Tiền cọc khi đặt phòng
    bookingSource VARCHAR(100) NULL,
    note TEXT NULL,
    staffName VARCHAR(100) NULL, -- lưu snapshot tên nhân viên đặt hộ tại quầy
    roomBookingStatusId INT NOT NULL,
    
    -- Cột Total sẽ dùng formula để tính. Lý do tại vì đang sử dụng khóa 
    -- lạc quan, nếu khách gọi nhiều service thì dễ xảy ra đụng độ dẫn tới trãi
    -- nghiệm kém hiệu quả, khi nào khách cần xem tổng tiền thì mới gọi forumla
    -- lên để tính, không cần lưu hardrow.
    
    voucherId INT NULL,
    paymentMethodId INT NOT NULL,
    receiptId INT NULL, -- có thể null, do mới book chứ chưa thanh toán
    userId INT NOT NULL,
    staffId INT NULL, -- NULL nếu đặt online
    FOREIGN KEY (roomBookingStatusId) REFERENCES RoomBookingStatus(roomBookingStatusId),
	FOREIGN KEY (voucherId) REFERENCES Voucher(voucherId),
    FOREIGN KEY (paymentMethodId) REFERENCES PaymentMethod(paymentMethodId),
	FOREIGN KEY (receiptId) REFERENCES Receipt(receiptId),
	CONSTRAINT FK_Booking_Customer FOREIGN KEY (userId) REFERENCES User(userId),
	CONSTRAINT FK_Booking_Staff FOREIGN KEY (staffId) REFERENCES User(userId)
);

CREATE TABLE RoomBookingDetail (
	roomBookingDetailId INT AUTO_INCREMENT PRIMARY KEY,
    price DECIMAL(15,2) DEFAULT 0.00,
    timeIn DATETIME NULL, -- timeIn và timeOut là thời gian vào ra của 1 phòng nào đó
    timeOut DATETIME NULL,
    roomName VARCHAR(155) NOT NULL, -- lưu snapshot
    roomBookingId INT NOT NULL,
    roomId INT NOT NULL,
	FOREIGN KEY (roomBookingId) REFERENCES RoomBooking(roomBookingId),
	FOREIGN KEY (roomId) REFERENCES Room(roomId)
);

CREATE TABLE RoomBookingService(
	roomBookingServiceId INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    unitServicePrice DECIMAL(15,2) DEFAULT 0.00,
    createAt DATETIME NOT NULL,
    serviceName VARCHAR(155), -- Lưu snapshot
	roomBookingDetailId INT NOT NULL,
    serviceId INT NOT NULL,
	FOREIGN KEY (roomBookingDetailId) REFERENCES RoomBookingDetail(roomBookingDetailId),
	FOREIGN KEY (serviceId) REFERENCES Service(serviceId)
);

CREATE TABLE ManualDiscountReason(
	manualDiscountReasonId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE ManualDiscountDetail(
	manualDiscountDetailId INT AUTO_INCREMENT PRIMARY KEY,
    amountDiscount DECIMAL(15,2) DEFAULT 0.00,
    manualDiscountReasonId INT NOT NULL,
    receiptId INT NOT NULL, 
	FOREIGN KEY (manualDiscountReasonId) REFERENCES ManualDiscountReason(manualDiscountReasonId),
	FOREIGN KEY (receiptId) REFERENCES Receipt(receiptId)
);

CREATE TABLE Feedback (
    feedbackId INT AUTO_INCREMENT PRIMARY KEY,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5), 
    comment TEXT NULL,
    createAt DATETIME NOT NULL,
    userId INT NOT NULL,
    roomBookingId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (roomBookingId) REFERENCES RoomBooking(roomBookingId)
);




