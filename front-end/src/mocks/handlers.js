import { http, HttpResponse, delay } from 'msw'


const db = {
  roles: [
    { id: 1, name: 'KhachHang' },
    { id: 2, name: 'LeTan' },
    { id: 3, name: 'BanQuanLy' }
  ],
  users: [
    { id: 1, username: 'khachhang1', email: 'khach@gmail.com', roleId: 1, fullName: 'Nguyễn Văn A' }
  ],
  roomCategories: [
    { id: 1, name: 'Standard', price: 500000, capacity: 2 },
    { id: 2, name: 'VIP', price: 1500000, capacity: 2 }
  ],
  rooms: [
    { id: 101, roomCategoryId: 1, status: 'Available' },
    { id: 102, roomCategoryId: 1, status: 'Occupied' },
    { id: 201, roomCategoryId: 2, status: 'Available' },
    { id: 103, roomCategoryId: 1, status: 'Available' },
    { id: 104, roomCategoryId: 1, status: 'Available' },
    { id: 105, roomCategoryId: 1, status: 'Occupied' },
    { id: 202, roomCategoryId: 2, status: 'Available' },
    { id: 203, roomCategoryId: 2, status: 'Occupied' },
    { id: 301, roomCategoryId: 1, status: 'Available' },
    { id: 302, roomCategoryId: 2, status: 'Available' },
    { id: 303, roomCategoryId: 1, status: 'Available' },
    { id: 304, roomCategoryId: 2, status: 'Available' },
    { id: 305, roomCategoryId: 1, status: 'Available' }
  ],
  services: [
    { id: 1, name: 'Spa & Massage', price: 300000 },
    { id: 2, name: 'Thuê xe máy', price: 150000 },
    { id: 3, name: 'Giặt ủi', price: 50000 }
  ],
  bookings: [
    {
      id: 1,
      userId: 1,
      bookingDate: '2026-05-09',
      status: 'Confirmed',
      totalAmount: 500000
    }
  ],
  bookingDetails: [
    { id: 1, bookingId: 1, roomId: 102, checkIn: '2026-05-10', checkOut: '2026-05-12' }
  ],
  invoices: []
}


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

    const availableRooms = db.rooms.filter(room => room.status === 'Available');

    const standardImages = [
      "https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=800",
      "https://images.pexels.com/photos/271618/pexels-photo-271618.jpeg?auto=compress&cs=tinysrgb&w=800",
      "https://images.pexels.com/photos/1743205/pexels-photo-1743205.jpeg?auto=compress&cs=tinysrgb&w=800",
      "https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg?auto=compress&cs=tinysrgb&w=800",
      "https://images.pexels.com/photos/262048/pexels-photo-262048.jpeg?auto=compress&cs=tinysrgb&w=800"
    ];

    const roomsWithDetails = availableRooms.map(room => {
      const category = db.roomCategories.find(c => c.id === room.roomCategoryId);
      let imgUrl = "";
      if (category?.name === 'VIP') {
        imgUrl = "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80"; 
      } else {

        imgUrl = standardImages[room.id % standardImages.length]; 
      }

      return {
        ...room,
        name: category ? category.name : 'Phòng tiêu chuẩn',
        price: category ? category.price : 0,
        image: imgUrl
      };
    });

    const startIndex = (page - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedRooms = roomsWithDetails.slice(startIndex, endIndex);

    return HttpResponse.json({
      data: paginatedRooms,
      totalPages: Math.ceil(roomsWithDetails.length / limit),
      currentPage: page
    });
  }),
]