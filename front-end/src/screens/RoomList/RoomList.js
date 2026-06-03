import { useEffect, useState, useMemo } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Card, Col, Container, Row, Stack } from "react-bootstrap";
import RoomSearchBar from "../../components/RoomSearchBar";
import RoomListDisplay from "../../components/RoomListDisplay";
import BookingSummary from "../../components/BookingSummary";
import BackButton from "../../components/BackButton";

const RoomList = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const searchData = location.state || {};
    const [searchParams, setSearchParams] = useState({
        roomType: searchData.roomType || "Theo phòng",
        priceRange: searchData.priceRange || [500000, 5000000],
        checkInDate: searchData.checkInDate || new Date(),
        checkOutDate: searchData.checkOutDate || null
    });

    useEffect(() => {
        if (location.state) {
            setSearchParams({
                roomType: location.state.roomType || "Theo phòng",
                priceRange: location.state.priceRange || [500000, 5000000],
                checkInDate: location.state.checkInDate || new Date(),
                checkOutDate: location.state.checkOutDate || null
            });
            setCurrentPage(1);
        }
    }, [location.state]);

    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [selectedRoom, setSelectedRoom] = useState(null);

    const handleBooking = (payload) => {
        navigate('/checkout', { state: { bookingSummary: payload } });
    };

    useEffect(() => {
        setLoading(true);


        const pad = (num) => String(num).padStart(2, '0');
        const formatBackendDate = (date) => {
            if (!date) return "";
            const d = new Date(date);
            return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
        };

        const params = new URLSearchParams();
        params.append("page", currentPage);

        const currentCheckIn = searchParams.checkInDate || new Date();
        params.append("checkIn", formatBackendDate(currentCheckIn));

        if (searchParams.checkOutDate) {
            params.append("checkOut", formatBackendDate(searchParams.checkOutDate));
        }

        if (searchParams.roomType === "Phòng thường") {
            params.append("roomTypeId", 1);
        } else if (searchParams.roomType === "Phòng VIP") {
            params.append("roomTypeId", 2);
        }

        if (searchParams.priceRange && searchParams.priceRange.length === 2) {
            params.append("minPrice", searchParams.priceRange[0]);
            params.append("maxPrice", searchParams.priceRange[1]);
        }

        const fetchUrl = `http://localhost:8080/HotelManagementServer/api/rooms?${params.toString()}`;


        fetch(fetchUrl)
            .then(res => {
                if (!res.ok) throw new Error("Yêu cầu lọc dữ liệu không hợp lệ.");
                return res.json();
            })
            .then(response => {

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
                    id: room.roomId || room.id,
                    name: room.name || room.roomName,
                    roomTypeName: room.roomTypeName || room.type,
                    price: room.price,
                    capacity: room.capacity,
                    avatarUrl: room.thumbnail || "https://images.unsplash.com/photo-1611892440504-42a792e24d32?q=80&w=1000&auto=format&fit=crop",
                    beds: room.beds || []
                }));

                setRooms(mappedRooms);
                
                const limitPerPage = 6; 
                if (roomList.length >= limitPerPage) {
                    setTotalPages(currentPage + 1); 
                } else {
                    setTotalPages(currentPage); 
                }

                setTimeout(() => { setLoading(false); }, 600);
            })
            .catch(error => {
                console.error("Lỗi khi lọc dữ liệu phòng:", error);
                setRooms([]);
                setLoading(false);
            });
    }, [currentPage, searchParams]);
    const handleSearchSubmit = (newParams) => {
        setSearchParams(newParams);
        setCurrentPage(1);
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
        const scrollContainer = document.querySelector('.bg-overlay');
        if (scrollContainer) scrollContainer.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const memoizedRoomList = useMemo(() => (
        <RoomListDisplay
            variant="list"
            rooms={rooms}
            loading={loading}
            selectedRoom={selectedRoom}
            onSelectRoom={setSelectedRoom}
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
            showPagination={true}
        />
    ), [rooms, loading, selectedRoom, currentPage, totalPages]);

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <Row className="g-4">
                <Col lg={8}>
                    <Stack className="mb-4">
                        <BackButton className="mb-4" />
                    </Stack>
                    <Card className="glass-card-static border-0 shadow-sm mb-5 p-3" style={{ zIndex: 1050, position: 'relative', overflow: 'visible' }}>
                        <RoomSearchBar onSearch={handleSearchSubmit} initialValues={searchParams} hoverClass="" />
                    </Card>

                    {memoizedRoomList}
                </Col>

                <Col lg={4}>
                    <BookingSummary
                        room={selectedRoom}
                        defaultCheckIn={searchParams.checkInDate ? new Date(searchParams.checkInDate).toISOString().split('T')[0] : ""}
                        defaultCheckOut={searchParams.checkOutDate ? new Date(searchParams.checkOutDate).toISOString().split('T')[0] : ""}
                        onAction={(payload) => handleBooking(payload)}
                    />
                </Col>
            </Row>

        </Container>
    );
};

export default RoomList;