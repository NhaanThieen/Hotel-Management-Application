import { http, HttpResponse, delay } from 'msw'


const db = {

  users: [
    { userId: 1, name: 'Nguyễn Văn A', phone: '0901234567', isDeleted: 0, avataURL: '', role: 'KHACH_HANG' }
  ],

  roomTypes: [
    { 
      roomTypeId: 1, 
      name: 'Standard', 
      price: 500000, 
      capacity: 2,
      avatarUrl: 'https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=800'
    },
    { 
      roomTypeId: 2, 
      name: 'VIP', 
      price: 1500000, 
      capacity: 2,
      avatarUrl: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80'
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
      totalAmount: 1000000 
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
      totalAmount: 3000000
    }
  ],

  roomBookingDetails: [
    { roomBookingDetailId: 1, roomBookingId: 1, roomId: 102, roomName: 'P102', price: 500000, timeIn: '2026-05-10T14:00:00.000Z', timeOut: '2026-05-12T12:00:00.000Z' },
    { roomBookingDetailId: 2, roomBookingId: 2, roomId: 201, roomName: 'V201', price: 1500000, timeIn: '2026-05-19T14:00:00.000Z', timeOut: '2026-05-21T12:00:00.000Z' }
  ]
};

export const handlers = [

  http.post('/api/auth/login', async ({ request }) => {
    await delay(500)
    const { username, password } = await request.json()

    const user = db.users.find(u => u.username === username)
    if (user) {
      return HttpResponse.json({
        message: 'Đăng nhập thành công',
        token: 'fake-jwt-token-12345',
        user: user
      })
    }
    return HttpResponse.json({ message: 'Sai tài khoản hoặc mật khẩu' }, { status: 401 })
  }),



  http.get('/api/services', () => {
    return HttpResponse.json(db.services)
  }),


  http.post('/api/bookings', async ({ request }) => {
    await delay(800)
    const payload = await request.json()

    const newBooking = {
      id: db.bookings.length + 1,
      userId: payload.userId,
      bookingDate: new Date().toISOString(),
      status: 'Pending',
      totalAmount: payload.totalAmount || 0
    }
    db.bookings.push(newBooking)

    const newBookingDetail = {
      id: db.bookingDetails.length + 1,
      bookingId: newBooking.id,
      roomId: payload.roomId,
      checkIn: payload.checkIn,
      checkOut: payload.checkOut
    }
    db.bookingDetails.push(newBookingDetail)

    const roomIndex = db.rooms.findIndex(r => r.id === payload.roomId)
    if (roomIndex !== -1) db.rooms[roomIndex].status = 'Booked'

    return HttpResponse.json(
      { message: 'Đặt phòng thành công!', data: newBooking },
      { status: 201 }
    )
  }),

  http.get('/api/bookings', ({ request }) => {
    const url = new URL(request.url)
    const userId = url.searchParams.get('userId')

    let result = db.bookings
    if (userId) {
      result = result.filter(b => b.userId === parseInt(userId))
    }
    const bookingsWithDetails = result.map(booking => {
      const details = db.bookingDetails.filter(d => d.bookingId === booking.id)
      return { ...booking, details }
    })

    return HttpResponse.json(bookingsWithDetails)
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
        avatarUrl: roomType ? roomType.avatarUrl : '' 
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
    const isVip = category?.name === 'VIP';

    const roomDetail = {
      id: room.roomId,
      roomName: room.roomName,
      name: category ? category.name : '',
      price: category ? category.price : 0,
      capacity: category ? category.capacity : 2,
      size: isVip ? '45m²' : '38m²',
      bedType: isVip ? '1 Giường King' : '2 Giường Đơn',
      description: isVip 
        ? "Trải nghiệm đỉnh cao của sự sang trọng với tầm nhìn toàn cảnh thành phố."
        : "Không gian ấm cúng, thiết kế hiện đại mang lại cảm giác thư thái tuyệt đối.",
      amenities: ['Wifi tốc độ cao', 'Smart TV 55 inch', 'Mini Bar', 'Bồn tắm', 'Máy pha cafe'],
      
      // SỬA: Avatar chính kéo từ Database
      avatarUrl: category ? category.avatarUrl : '', 
      
      // Ảnh lướt xem có thể tách bảng RoomImage sau này, giờ mock tạm
      images: isVip 
          ? ["https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80", "https://images.pexels.com/photos/271618/pexels-photo-271618.jpeg?auto=compress&cs=tinysrgb&w=800"]
          : ["https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=800", "https://images.pexels.com/photos/1743205/pexels-photo-1743205.jpeg?auto=compress&cs=tinysrgb&w=800"]
    };
    return HttpResponse.json(roomDetail);
  })
];
