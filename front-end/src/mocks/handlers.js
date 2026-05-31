import { http, HttpResponse, delay } from 'msw'


const db = {

  users: [
    { userId: 1, name: 'Nguyễn Văn A', userName: 'thien@gmail.com', password: 'Abc@1234', phone: '0901234567', isDeleted: 0, avataURL: '', role: 'KHACH_HANG', memberTierId: 3 }
  ],

  memberTiers: [
    { memberTierId: 1, type: 'Classic', memberDiscountPercent: 0 },
    { memberTierId: 2, type: 'Silver', memberDiscountPercent: 5 },
    { memberTierId: 3, type: 'Gold', memberDiscountPercent: 10 },
    { memberTierId: 4, type: 'Diamond', memberDiscountPercent: 15 }
  ],

  amenities: [
    { amenityId: 1, name: 'Wifi tốc độ cao' },
    { amenityId: 2, name: 'Smart TV 55 inch' },
    { amenityId: 3, name: 'Mini Bar' },
    { amenityId: 4, name: 'Bồn tắm sục Jacuzzi' },
    { amenityId: 5, name: 'Bồn tắm đứng' },
    { amenityId: 6, name: 'Máy pha cafe' }
  ],

  services: [
    { serviceId: 1, name: 'Massage Body Thụy Điển', price: 850000, stock: 50, isDeleted: 0, imgURL: 'https://images.unsplash.com/photo-1544161515-4ab6ce6db874?auto=format&fit=crop&w=800&q=80', type: 'Spa & Wellness' },
    { serviceId: 2, name: 'Bữa sáng Buffet Á-Âu', price: 350000, stock: 100, isDeleted: 0, imgURL: 'https://images.unsplash.com/photo-1493770348161-369560ae357d?auto=format&fit=crop&w=800&q=80', type: 'Ẩm thực' },
    { serviceId: 3, name: 'Xe đưa đón Sân bay (1 chiều)', price: 400000, stock: 20, isDeleted: 0, imgURL: 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?auto=format&fit=crop&w=800&q=80', type: 'Di chuyển' },
    { serviceId: 4, name: 'Dịch vụ Giặt ủi cao cấp', price: 150000, stock: 999, isDeleted: 0, imgURL: 'https://images.unsplash.com/photo-1582735689369-4fe89db7114c?auto=format&fit=crop&w=800&q=80', type: 'Tiện ích' },
    { serviceId: 5, name: 'Trà chiều Hoàng gia Anh', price: 250000, stock: 30, isDeleted: 0, imgURL: 'https://images.unsplash.com/photo-1571407921609-8d184ebf32b8?auto=format&fit=crop&w=800&q=80', type: 'Ẩm thực' },
    { serviceId: 6, name: 'Thuê xe đạp khám phá', price: 100000, stock: 15, isDeleted: 0, imgURL: 'https://images.unsplash.com/photo-1485965120184-e220f721d03e?auto=format&fit=crop&w=800&q=80', type: 'Di chuyển' }
  ],

  roomBookingDetails: [
    { roomBookingDetailId: 1, roomBookingId: 1, roomId: 102, roomName: 'P102', price: 500000, timeIn: '2026-05-10T14:00:00.000Z', timeOut: '2026-05-12T12:00:00.000Z' },
    { roomBookingDetailId: 2, roomBookingId: 2, roomId: 201, roomName: 'V201', price: 1500000, timeIn: '2026-05-19T14:00:00.000Z', timeOut: '2026-05-21T12:00:00.000Z' }
  ],

  roomBookingServices: [],

  roomTypeImages: [
    { imageId: 1, roomTypeId: 1, imageUrl: 'https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=800' },
    { imageId: 2, roomTypeId: 1, imageUrl: 'https://images.pexels.com/photos/1743205/pexels-photo-1743205.jpeg?auto=compress&cs=tinysrgb&w=800' },
    { imageId: 3, roomTypeId: 2, imageUrl: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80' },
    { imageId: 4, roomTypeId: 2, imageUrl: 'https://images.pexels.com/photos/271618/pexels-photo-271618.jpeg?auto=compress&cs=tinysrgb&w=800' }
  ],



  roomTypeAmenities: [
    { roomTypeId: 1, amenityId: 1 },
    { roomTypeId: 1, amenityId: 2 },
    { roomTypeId: 1, amenityId: 5 },
    { roomTypeId: 2, amenityId: 1 },
    { roomTypeId: 2, amenityId: 2 },
    { roomTypeId: 2, amenityId: 3 },
    { roomTypeId: 2, amenityId: 4 },
    { roomTypeId: 2, amenityId: 6 }
  ],


  roomTypes: [
    {
      roomTypeId: 1,
      name: 'Standard',
      price: 500000,
      capacity: 2,
      avatarUrl: 'https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=800',
      size: '38m²',
      bedType: '2 Giường Đơn',
      description: 'Không gian ấm cúng, thiết kế hiện đại mang lại cảm giác thư thái tuyệt đối sau một ngày dài khám phá.'
    },
    {
      roomTypeId: 2,
      name: 'VIP',
      price: 1500000,
      capacity: 2,
      avatarUrl: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80',
      size: '45m²',
      bedType: '1 Giường King',
      description: 'Trải nghiệm đỉnh cao của sự sang trọng với tầm nhìn toàn cảnh thành phố, nội thất đẳng cấp quốc tế.'
    }
  ],

  rooms: [
    { roomId: 101, roomName: 'P101', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 102, roomName: 'P102', roomTypeId: 1, roomStatus: 'OCCUPIED', isDeleted: 0, version: 1 },
    { roomId: 201, roomName: 'V201', roomTypeId: 2, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 103, roomName: 'P103', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 104, roomName: 'P104', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 202, roomName: 'P202', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 103, roomName: 'P103', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 203, roomName: 'P203', roomTypeId: 2, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 104, roomName: 'P104', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 105, roomName: 'P105', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 301, roomName: 'P301', roomTypeId: 1, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 },
    { roomId: 302, roomName: 'V302', roomTypeId: 2, roomStatus: 'AVAILABLE', isDeleted: 0, version: 1 }
  ],

  roomBookings: [
    {
      roomBookingId: 1,
      userName: 'Nguyễn Văn A',
      dayStart: '2026-05-10',
      dayEnd: '2026-05-12',
      timeStart: '14:00',
      timeEnd: '12:00',
      voucherDiscountMoney: 0,
      depositAmount: 500000,
      totalAmount: 1000000,
      staffId: 101,
      staffName: 'Trần Lễ Tân',
      note: 'Khách yêu cầu phòng yên tĩnh',
      bookingSource: 1
    },
    {
      roomBookingId: 2,
      userName: 'Trần Thị B',
      dayStart: '2026-05-19',
      dayEnd: '2026-05-21',
      timeStart: '14:00',
      timeEnd: '12:00',
      voucherDiscountMoney: 0,
      depositAmount: 1000000,
      totalAmount: 3000000,
      staffId: 101,
      staffName: 'Trần Lễ Tân',
      note: 'Khách yêu cầu phòng yên tĩnh',
      bookingSource: 1
    }
  ],

  roomBookingDetails: [
    { roomBookingDetailId: 1, roomBookingId: 1, roomId: 102, roomName: 'P102', price: 500000, timeIn: '2026-05-10T14:00:00.000Z', timeOut: '2026-05-12T12:00:00.000Z' },
    { roomBookingDetailId: 2, roomBookingId: 2, roomId: 201, roomName: 'V201', price: 1500000, timeIn: '2026-05-19T14:00:00.000Z', timeOut: '2026-05-21T12:00:00.000Z' }
  ],

  reviews: []
};

let lastWalkInService = null;


export const handlers = [

http.post('/api/reviews', async ({ request }) => {
    await delay(800); 
    const payload = await request.json();

    const newReview = {
        reviewId: db.reviews.length + 1,
        bookingId: payload.bookingId,
        ratings: payload.ratings,
        tags: payload.tags,
        comment: payload.comment,
        createdAt: new Date().toISOString()
    };
    db.reviews.push(newReview);

    console.log("--> BE đã nhận Đánh giá mới:", newReview);

    return HttpResponse.json(
      { message: 'Gửi đánh giá thành công!', data: newReview },
      { status: 201 }
    );
  }),



  http.get('/api/history', ({ request }) => {
    const url = new URL(request.url);
    const userName = url.searchParams.get('userName');

    const userBookings = db.roomBookings.filter(b => b.userName === userName);
    const mappedBookings = userBookings.map(b => {
      const details = db.roomBookingDetails.filter(d => d.roomBookingId === b.roomBookingId);
      
      const isPast = new Date(b.dayEnd) < new Date();
      let currentStatus = "Đang chờ duyệt";
      let currentBg = "warning";

      if (details.length > 0) {
          if (isPast) {
              currentStatus = "Đã trả phòng"; 
              currentBg = "secondary";       
          } else {
              currentStatus = "Đã xác nhận";
              currentBg = "success";
          }
      }

      return {
        id: b.roomBookingId,
        type: "ROOM",
        title: `Mã hóa đơn: 10000#${b.roomBookingId}`,
        badgeText: currentStatus,
        badgeBg: currentBg,
        subText: `Hình thức: ${b.bookingSource === 1 ? "Website trực tuyến" : "Tại quầy"}`,
        amount: b.totalAmount,
        raw: { ...b, details }
      };
    });


    

    const userServices = db.roomBookingServices.filter(s => s.userName === userName);
    const walkInServices = userServices.filter(s => !db.roomBookings.some(b => b.roomBookingId === s.roomBookingId));

    const groupedReceipts = {};
    walkInServices.forEach(s => {
      if (!groupedReceipts[s.roomBookingId]) {
        groupedReceipts[s.roomBookingId] = { receiptId: s.roomBookingId, totalAmount: 0, services: [] };
      }
      groupedReceipts[s.roomBookingId].services.push(s);
      groupedReceipts[s.roomBookingId].totalAmount += s.quantity * s.unitPriceAtUse;
    });

    const mappedWalkIns = Object.values(groupedReceipts).map(w => ({
      id: w.receiptId,
      type: "SERVICE",
      title: `Mã hóa đơn: ${w.receiptId}`,
      badgeText: "Đã thanh toán",
      badgeBg: "success",
      subText: "Thanh toán tiện ích",
      amount: w.totalAmount,
      raw: w
    }));

    const unifiedHistory = [...mappedBookings, ...mappedWalkIns].sort((a, b) => b.id - a.id);

    return HttpResponse.json({ data: unifiedHistory });
  }),

  http.post('/api/services/book', async ({ request }) => {
    await delay(600);
    const payload = await request.json();
    const currentUserName = payload.userName;

    // 1. Lưu vào Database
    const newServiceRecord = {
      roomBookingServiceId: Date.now(),
      roomBookingId: db.roomBookings.find(b => b.userName === currentUserName)?.roomBookingId || Date.now(),
      userName: currentUserName,
      serviceName: payload.serviceName,
      quantity: 1,
      unitPriceAtUse: payload.price
    };
    db.roomBookingServices.push(newServiceRecord);

    return HttpResponse.json({
      message: "Đăng ký thành công!",
      receiptId: newServiceRecord.roomBookingId 
    }, { status: 200 });
  }),

  http.get('/api/receipts/:id', async ({ params }) => {
    await delay(500);
    const receiptId = parseInt(params.id, 10);

    const servicesUsed = db.roomBookingServices.filter(s => s.roomBookingId === receiptId);
    const totalServicesAmount = servicesUsed.reduce((sum, s) => sum + (s.quantity * s.unitPriceAtUse), 0);

    const booking = db.roomBookings.find(b => b.roomBookingId === receiptId);

    if (!booking) {
      if (servicesUsed.length === 0) {
        return HttpResponse.json({ message: "Hóa đơn trống hoặc không tồn tại" }, { status: 404 });
      }

      const targetUserName = servicesUsed[0].userName;
      const userProfile = db.users.find(u => u.userName === targetUserName) || {};

      return HttpResponse.json({
        receiptId: receiptId,
        userName: userProfile.name || "Khách vãng lai chưa cập nhật tên",
        userPhone: userProfile.phone || "Không có",
        userPaidId: userProfile.userId || "WALK-IN",
        timeCheckIn: "Không lưu trú phòng",
        timeCheckOut: "Không lưu trú phòng",
        staffId: 102,
        staffName: "Lễ tân Dịch vụ",
        vipDiscountAmount: 0,
        totalPrice: totalServicesAmount,
        roomAmount: 0,
        roomName: "Không đăng ký phòng",
        roomTypeName: "Tiện ích ngoài bãi",
        pricePerNight: 0,
        totalNights: 0,
        services: servicesUsed
      });
    }


    const userProfile = db.users.find(u => u.userName === booking.userName) || {};
    const bookingDetails = db.roomBookingDetails.filter(d => d.roomBookingId === receiptId);
    const mainDetail = bookingDetails[0] || {};
    const room = db.rooms.find(r => r.roomId === mainDetail.roomId) || {};
    const roomType = db.roomTypes.find(t => t.roomTypeId === room.roomTypeId) || {};

    const startDate = new Date(booking.dayStart);
    const endDate = new Date(booking.dayEnd);
    const diffTime = Math.abs(endDate - startDate);
    const totalNights = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) || 1;

    const roomPricePerNight = mainDetail.price || 0;
    const totalRoomAmount = roomPricePerNight * totalNights;
    const subTotal = totalRoomAmount + totalServicesAmount;

    let vipDiscountAmount = 0;
    if (userProfile.memberTierId) {
      const tier = db.memberTiers.find(t => t.memberTierId === userProfile.memberTierId);
      if (tier) {
        vipDiscountAmount = (subTotal * tier.memberDiscountPercent) / 100;
      }
    }

    return HttpResponse.json({
      receiptId: receiptId,
      userName: userProfile.name || "Khách ẩn danh",
      userPhone: userProfile.phone || "Không xác định",
      userPaidId: userProfile.userId || "N/A",
      timeCheckIn: `${booking.timeStart} - ${booking.dayStart}`,
      timeCheckOut: `${booking.timeEnd} - ${booking.dayEnd}`,
      staffId: booking.staffId || "Hệ Thống",
      staffName: booking.staffName || "Auto-Booking",
      vipDiscountAmount: vipDiscountAmount,
      totalPrice: subTotal - vipDiscountAmount,
      roomAmount: totalRoomAmount,
      roomName: mainDetail.roomName || "N/A",
      roomTypeName: roomType.name || "N/A",
      pricePerNight: roomPricePerNight,
      totalNights: totalNights,
      services: servicesUsed
    });
  }),


 http.get('/api/customer/profile', async ({ request }) => {
    await delay(400);
    
    const url = new URL(request.url);
    const userName = url.searchParams.get('userName');
    const user = db.users.find(u => u.userName === userName);
    
    if (!user) return HttpResponse.json({ message: "Chưa đăng nhập" }, { status: 401 });

    const tier = db.memberTiers.find(t => t.memberTierId === user.memberTierId);

    return HttpResponse.json({
      userId: user.userId,
      name: user.name,
      username: user.userName, 
      phone: user.phone,
      role: user.role,
      tierName: tier ? tier.type : 'Classic',
      discountPercent: tier ? tier.memberDiscountPercent : 0,
      avataURL: user.avataURL || 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=150&h=150&q=80'
    });
  }),

  http.put('/api/customer/profile', async ({ request }) => {
    console.log("-> Đã chặn được request PUT tới /api/customer/profile");
    
    await delay(500);
    const payload = await request.json();
    
    console.log("-> Payload nhận được:", payload);

    const userIndex = db.users.findIndex(u => u.userName === payload.userName); 
    
    console.log("-> Kết quả tìm kiếm index:", userIndex);

    if (userIndex !== -1) {
      db.users[userIndex].name = payload.name;
      db.users[userIndex].phone = payload.phone;

      if (payload.password) {
        db.users[userIndex].password = payload.password;
      }

      return HttpResponse.json({
        message: "Cập nhật thông tin tài khoản thành công!",
        data: db.users[userIndex]
      });
    }
    
    return HttpResponse.json({ message: "Không tìm thấy user" }, { status: 404 });
  }),

  http.post('/api/auth/login', async ({ request }) => {
    await delay(500)
    const { userName, password } = await request.json()

    const user = db.users.find(u => u.userName === userName && u.password === password)
    if (user) {
      return HttpResponse.json({
        message: 'Đăng nhập thành công',
        token: 'fake-jwt-token-12345',
        user: {
          userId: user.userId,
          name: user.name,
          userName: user.userName,
          phone: user.phone,
          role: user.role,
          avataURL: user.avataURL
        }
      })
    }
    return HttpResponse.json({ message: 'Tài khoản hoặc mật khẩu không chính xác!' }, { status: 401 })
  }),

  http.post('/api/auth/register', async ({ request }) => {
    await delay(600)
    const payload = await request.json()

    const isExisted = db.users.some(u => u.userName === payload.userName)
    if (isExisted) {
      return HttpResponse.json({ message: 'Tài khoản này đã tồn tại trên hệ thống!' }, { status: 400 })
    }

    const newUser = {
      userId: db.users.length + 1,
      userName: payload.userName,
      password: payload.password,
      name: payload.name,
      phone: payload.phone,
      isDeleted: 0,
      avataURL: '',
      role: 'KHACH_HANG'
    }
    db.users.push(newUser)

    return HttpResponse.json({ message: 'Đăng ký tài khoản thành công!', data: newUser }, { status: 201 })
  }),

  http.post('/api/auth/google', async ({ request }) => {
    await delay(600); 
    const payload = await request.json();
    const token = payload.token;
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    
    const googleData = JSON.parse(jsonPayload);
    
    const realEmail = googleData.email;
    const realName = googleData.name;
    const realAvatar = googleData.picture;

    let existingUser = db.users.find(u => u.userName === realEmail);

    if (!existingUser) {
        existingUser = {
            userId: db.users.length + 1,
            name: realName,          
            userName: realEmail,      
            phone: 'Chưa cập nhật', 
            password: '', 
            role: 'KHACH_HANG',
            memberTierId: 1, 
            avataURL: realAvatar      
        };
        db.users.push(existingUser);
        console.log("--> MSW: Đã tạo tài khoản với data thật từ Google:", existingUser);
    }

    return HttpResponse.json({
      message: 'Đăng nhập Google thành công!',
      token: 'fake-jwt-token-google-99999',
      user: existingUser
    }, { status: 200 });
  }),


  http.get('/api/services', () => {
    return HttpResponse.json(db.services)
  }),

  http.post('/api/bookings', async ({ request }) => {
    await delay(800)
    const payload = await request.json()

    const newRoomBooking = {
      roomBookingId: db.roomBookings.length + 1,
      userName: payload.userName || 'khach_vang_lai',
      dayStart: payload.dayStart,
      dayEnd: payload.dayEnd,
      timeStart: payload.timeStart || '14:00',
      timeEnd: payload.timeEnd || '12:00',
      voucherDiscountMoney: payload.voucherDiscountMoney || 0,
      depositAmount: payload.depositAmount || 0,
      totalAmount: payload.totalAmount || 0,

      staffId: null,
      staffName: null,
      note: payload.note || '',
      bookingSource: payload.bookingSource || 1
    }
    db.roomBookings.push(newRoomBooking)

    const newRoomBookingDetail = {
      roomBookingDetailId: db.roomBookingDetails.length + 1,
      roomBookingId: newRoomBooking.roomBookingId,
      roomId: payload.roomId,
      roomName: payload.roomName,
      price: payload.pricePerNight,
      timeIn: `${payload.dayStart}T${newRoomBooking.timeStart}:00.000Z`,
      timeOut: `${payload.dayEnd}T${newRoomBooking.timeEnd}:00.000Z`
    }
    db.roomBookingDetails.push(newRoomBookingDetail)

    const roomIndex = db.rooms.findIndex(r => r.roomId === payload.roomId)
    if (roomIndex !== -1) db.rooms[roomIndex].roomStatus = 'OCCUPIED'

    return HttpResponse.json(
      { message: 'Đặt phòng thành công! Cảm ơn bạn đã chọn OU Hotel.', data: newRoomBooking },
      { status: 201 }
    )
  }),

  http.get('/api/bookings', ({ request }) => {
    const url = new URL(request.url)
    const userName = url.searchParams.get('userName')

    let result = db.roomBookings
    if (userName) {
      result = result.filter(b => b.userName === userName)
    }
    const bookingsWithDetails = result.map(booking => {
      const details = db.roomBookingDetails.filter(d => d.roomBookingId === booking.roomBookingId)
      return { ...booking, details }
    })

    return HttpResponse.json({ data: bookingsWithDetails })
  }),


  http.post('/api/invoices', async ({ request }) => {
    const payload = await request.json()

    const newInvoice = {
      id: db.invoices.length + 1,
      bookingId: payload.bookingId,
      issueDate: new Date().toISOString(),
      status: 'Paid'
    }
    db.invoices.push(newInvoice)

    const bookingIndex = db.bookings.findIndex(b => b.id === payload.bookingId)
    if (bookingIndex !== -1) db.bookings[bookingIndex].status = 'Confirmed'

    return HttpResponse.json({ message: 'Thanh toán và xuất hóa đơn thành công', data: newInvoice })
  }),

  http.get('/api/rooms', ({ request }) => {
    const url = new URL(request.url);
    const page = parseInt(url.searchParams.get('page') || '1', 10);
    const limit = parseInt(url.searchParams.get('limit') || '6', 10);
    const type = url.searchParams.get('type') || 'all';
    const price = url.searchParams.get('price') || 'all';
    const reqCheckIn = url.searchParams.get('checkIn');
    const reqCheckOut = url.searchParams.get('checkOut');

    let conflictingRoomIds = [];
    if (reqCheckIn && reqCheckOut) {
      const searchIn = new Date(reqCheckIn);
      const searchOut = new Date(reqCheckOut);
      conflictingRoomIds = db.roomBookingDetails
        .filter(detail => new Date(detail.timeIn) < searchOut && new Date(detail.timeOut) > searchIn)
        .map(detail => detail.roomId);
    }

    const availableRooms = db.rooms.filter(room =>
      room.roomStatus === 'AVAILABLE' && room.isDeleted === 0 && !conflictingRoomIds.includes(room.roomId)
    );

    let roomsWithDetails = availableRooms.map(room => {
      const roomType = db.roomTypes.find(c => c.roomTypeId === room.roomTypeId);
      return {
        id: room.roomId,
        roomName: room.roomName,
        name: roomType ? roomType.name : 'Phòng tiêu chuẩn',
        price: roomType ? roomType.price : 0,
        avatarUrl: roomType ? roomType.avatarUrl : '',
        size: roomType ? roomType.size : '',
        capacity: roomType ? roomType.capacity : 2
      };
    });

    if (type !== 'all') {
      if (type === 'Phòng VIP') roomsWithDetails = roomsWithDetails.filter(room => room.name === 'VIP');
      else if (type === 'Phòng thường') roomsWithDetails = roomsWithDetails.filter(room => room.name === 'Standard');
    }
    if (price !== 'all') {
      const priceParts = price.split('_');
      if (priceParts.length === 2) {
        roomsWithDetails = roomsWithDetails.filter(room => room.price >= parseInt(priceParts[0], 10) && room.price <= parseInt(priceParts[1], 10));
      }
    }

    const startIndex = (page - 1) * limit;
    return HttpResponse.json({
      data: roomsWithDetails.slice(startIndex, startIndex + limit),
      totalPages: Math.ceil(roomsWithDetails.length / limit) || 1,
      currentPage: page
    });
  }),
  http.get('/api/rooms/:id', ({ params }) => {
    const roomId = parseInt(params.id, 10);
    const room = db.rooms.find(r => r.roomId === roomId);
    if (!room) return HttpResponse.json({ message: "Không tìm thấy phòng" }, { status: 404 });

    const category = db.roomTypes.find(c => c.roomTypeId === room.roomTypeId);

    const images = db.roomTypeImages
      .filter(img => img.roomTypeId === room.roomTypeId)
      .map(img => img.imageUrl);


    const isVip = category?.name === 'VIP';

    const amenities = db.roomTypeAmenities
      .filter(rta => rta.roomTypeId === room.roomTypeId)
      .map(rta => {
        const amenityDict = db.amenities.find(a => a.amenityId === rta.amenityId);
        return amenityDict ? amenityDict.name : '';
      })
      .filter(name => name !== '');


    const roomDetail = {
      id: room.roomId,
      roomName: room.roomName,
      name: category ? category.name : '',
      price: category ? category.price : 0,
      capacity: category ? category.capacity : 2,
      size: category ? category.size : '',
      bedType: category ? category.bedType : '',
      description: category ? category.description : '',
      images: images,
      avatarUrl: category ? category.avatarUrl : '',
      amenities: amenities
    };
    return HttpResponse.json(roomDetail);
  })
];
