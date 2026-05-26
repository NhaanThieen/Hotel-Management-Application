import { useEffect, useState, useMemo } from "react"; 
import { useLocation, useNavigate } from "react-router-dom";
import { Card, Col, Container, Row, Stack } from "react-bootstrap";
import RoomSearchBar from "../../components/RoomSearchBar";
import RoomListDisplay from "../../components/RoomListDisplay";
import BookingSummary from "../../components/BookingSummary";
import BackButton from "../../components/BackButton";

const RoomList = () => {
    const location = useLocation();
    const searchData = location.state || {};
    const navigate = useNavigate();
    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const limitPerPage = 9;
    const [selectedRoom, setSelectedRoom] = useState(null);

    const [searchParams, setSearchParams] = useState({
        roomType: searchData.roomType || "Theo phòng",
        priceRange: searchData.priceRange || [500000, 5000000],
        checkInDate: searchData.checkInDate || new Date(),
        checkOutDate: searchData.checkOutDate || null
    });

    const handleBooking = (payload) => {
        navigate('/checkout', { state: { bookingSummary: payload } });
    };

    useEffect(() => {
        setLoading(true);
        const typeParam = searchParams.roomType === "Theo phòng" || searchParams.roomType === "Tất cả phòng" ? "all" : searchParams.roomType;
        let priceParam = "all";
        if (searchParams.priceRange[0] > 500000 || searchParams.priceRange[1] < 5000000) {
            priceParam = `${searchParams.priceRange[0]}_${searchParams.priceRange[1]}`;
        }
        const checkInParam = searchParams.checkInDate ? new Date(searchParams.checkInDate).toISOString() : "";
        const checkOutParam = searchParams.checkOutDate ? new Date(searchParams.checkOutDate).toISOString() : "";

        const fetchUrl = `/api/rooms?page=${currentPage}&limit=${limitPerPage}&type=${typeParam}&price=${priceParam}&checkIn=${checkInParam}&checkOut=${checkOutParam}`;
        
        fetch(fetchUrl)
            .then(res => res.json())
            .then(response => {
                const roomData = Array.isArray(response.data) ? response.data : (Array.isArray(response) ? response : []);
                setRooms(roomData);
                setTotalPages(response.totalPages || 1);
                setTimeout(() => { setLoading(false); }, 1000);
            })
            .catch(error => {
                console.error("Lỗi:", error);
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