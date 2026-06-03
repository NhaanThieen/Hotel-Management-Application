import { useEffect, useState } from "react";
import { Button, Card, Col, Container, Pagination, Row, Stack } from "react-bootstrap";
import MySpinner from "../../components/MySpinner";
import RoomListDisplay from "../../components/RoomListDisplay";

const Home = () => {

    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const limitPerPage = 6; 

    useEffect(() => {
        setLoading(true);

        const pad = (num) => String(num).padStart(2, '0');
        const now = new Date();
        const checkInStr = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`;

       fetch(`http://localhost:8080/HotelManagementServer/api/rooms?page=${currentPage}&checkIn=${encodeURIComponent(checkInStr)}`)
            .then(res => {
                if (!res.ok) throw new Error("Lỗi cấu hình yêu cầu từ hệ thống.");
                return res.json();
            })
            .then(response => {
                console.log("Cục JSON gốc từ Backend:", response);

                let roomList = [];
                if (response.data && Array.isArray(response.data.rooms)) {
                    roomList = response.data.rooms; 
                } else if (Array.isArray(response.data)) {
                    roomList = response.data; 
                } else if (Array.isArray(response.rooms)) {
                    roomList = response.rooms; 
                } else if (Array.isArray(response)) {
                    roomList = response;
                }


               const mappedRooms = roomList.map(room => ({
                    id: room.roomId,                    
                    name: room.name,                     
                    roomTypeName: room.roomTypeName,    
                    price: room.price,                   
                    capacity: room.capacity,             
                    avatarUrl: room.thumbnail || "https://images.unsplash.com/photo-1611892440504-42a792e24d32?q=80&w=1000&auto=format&fit=crop",             
                    beds: room.beds || []
                }));

                setRooms(mappedRooms);

                if (roomList.length > limitPerPage) {
                    setTotalPages(currentPage + 1);
                } else {
                    setTotalPages(currentPage); 
                }

                setTimeout(() => {
                    setLoading(false);
                }, 500);
            })
            .catch(error => {
                console.error("Lỗi khi kéo dữ liệu phòng:", error);
                setRooms([]);
                setLoading(false);
            });
    }, [currentPage]);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
        const scrollContainer = document.querySelector('.bg-overlay');
        if (scrollContainer) {
            scrollContainer.scrollTo({ top: 0, behavior: 'smooth' });
        }
    };

    return (
        <Container className="my-5 py-5">
            <Stack className="text-center mb-5">
                <Stack as="h2" className="text-gold luxury-title display-5 fw-bold mb-3">
                    Phòng Nổi Bật
                </Stack>
                <Stack as="p" className="text-white-50">
                    Khám phá không gian nghỉ dưỡng tinh tế
                </Stack>
            </Stack>

            <RoomListDisplay 
                variant="grid" 
                rooms={rooms} 
                loading={loading} 
            />

            {totalPages > 1 && (
                <Stack direction="horizontal" className="justify-content-center mt-5">
                    <Pagination className="custom-gold-pagination">
                        <Pagination.Prev
                            onClick={() => handlePageChange(currentPage - 1)}
                            disabled={currentPage === 1}
                        />

                        {[...Array(totalPages)].map((_, index) => (
                            <Pagination.Item
                                key={index + 1}
                                active={index + 1 === currentPage}
                                onClick={() => handlePageChange(index + 1)}
                            >
                                {index + 1}
                            </Pagination.Item>
                        ))}

                        <Pagination.Next
                            onClick={() => handlePageChange(currentPage + 1)}
                            disabled={currentPage === totalPages}
                        />
                    </Pagination>
                </Stack>
            )}
        </Container>
    );
};

export default Home;