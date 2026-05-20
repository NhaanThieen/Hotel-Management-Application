import { Button, Card, Col, Row, Stack, Pagination } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import MySpinner from "./MySpinner";

const RoomListDisplay = ({
    variant = "list",
    rooms,
    loading,
    selectedRoom,
    onSelectRoom,
    currentPage,
    totalPages,
    onPageChange,
    showPagination = false
}) => {

    const navigate = useNavigate();

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    if (loading) return <MySpinner />;

    if (!rooms || rooms.length === 0) {
        return <Stack className="text-center text-white-50 py-5">Không tìm thấy phòng phù hợp.</Stack>;
    }

    const renderGridCard = (room) => (
        <Col lg={4} md={6} sm={12} key={room.id}>
            <Card className="glass-card h-100 border-0 shadow-lg overflow-hidden">
                <Stack className="position-relative overflow-hidden">
                    <Card.Img variant="top" src={room.avatarUrl} className="room-img" style={{ objectFit: 'cover', height: '240px' }} />                </Stack>
                <Card.Body className="d-flex flex-column p-4">
                    <Card.Title as={Stack} className="text-white fw-bold mb-3 fs-4">{room.name}</Card.Title>
                    <Stack direction="horizontal" className="mt-auto justify-content-between align-items-end">
                        <Stack>
                            <Card.Text as={Stack} className="text-white-50 d-block mb-1 small">Giá mỗi đêm từ</Card.Text>
                            <Card.Text as={Stack} className="price-tag fs-5 text-gold fw-bold">{formatVND(room.price)}</Card.Text>
                        </Stack>
                        <Button variant="outline-light" className="btn-luxury-glow px-4 py-2 fw-bold" onClick={() => navigate(`/rooms/${room.id}`)}>Chi tiết</Button>
                    </Stack>
                </Card.Body>
            </Card>
        </Col>
    );

    const renderListCard = (room) => (
        <Card key={room.id} className="glass-card mb-4 border-0 shadow-lg overflow-hidden">
            <Row className="g-0">
                <Col md={5} xl={4}>
                    <Card.Img src={room.avatarUrl} className="h-100" style={{ objectFit: 'cover', minHeight: '200px' }} />
                </Col>
                <Col md={7} xl={8}>
                    <Card.Body className="d-flex flex-column h-100 p-4">
                        <Stack className="mb-3">
                            <Card.Title as={Stack} className="text-white fw-bold fs-4">{room.name}</Card.Title>
                            <Card.Text as={Stack} direction="horizontal" className="text-white-50 small mb-2 align-items-center">
                                <Stack as="i" className="bi bi-person me-1"></Stack> Tối đa 2 người lớn & 1 trẻ em
                                <Stack as="span" className="mx-2">|</Stack>
                                <Stack as="i" className="bi bi-arrows-fullscreen me-1"></Stack> 38m²
                            </Card.Text>
                            <Stack
                                direction="horizontal"
                                className="text-gold text-decoration-none small align-items-center"
                                style={{ cursor: 'pointer' }}
                                onClick={() => navigate(`/rooms/${room.id}`)}
                            >
                                Xem chi tiết phòng <Stack as="i" className="bi bi-chevron-right ms-1"></Stack>
                            </Stack>
                        </Stack>
                        <Stack className="mt-auto pt-3 border-top border-secondary">
                            <Stack direction="horizontal" className="justify-content-between align-items-center">
                                <Stack>
                                    <Stack direction="horizontal" className="text-white fw-bold mb-1 align-items-center">
                                        <Stack as="i" className="bi bi-patch-check-fill text-success me-2"></Stack>
                                        Giá linh hoạt (Có thể hủy)
                                    </Stack>
                                    <Card.Text as={Stack} className="text-white-50 d-block small">Đã bao gồm bữa sáng</Card.Text>
                                </Stack>
                                <Stack className="text-end">
                                    <Card.Text as={Stack} className="text-white fw-bold mb-2 fs-5">{formatVND(room.price)}</Card.Text>
                                    <Button
                                        variant={selectedRoom?.id === room.id ? "success" : "outline-warning"}
                                        className="px-4 fw-bold rounded-pill"
                                        onClick={() => onSelectRoom && onSelectRoom(room)}
                                    >
                                        {selectedRoom?.id === room.id ? "Đã chọn" : "Chọn phòng"}
                                    </Button>
                                </Stack>
                            </Stack>
                        </Stack>
                    </Card.Body>
                </Col>
            </Row>
        </Card>
    );

    return (
        <Stack>
            {variant === "grid" ? (
                <Row className="g-4 justify-content-center">
                    {rooms.map(renderGridCard)}
                </Row>
            ) : (
                <Stack>
                    {rooms.map(renderListCard)}
                </Stack>
            )}

            {showPagination && totalPages > 1 && (
                <Stack direction="horizontal" className="justify-content-center mt-5">
                    <Pagination className="custom-gold-pagination">
                        <Pagination.Prev onClick={() => onPageChange && onPageChange(currentPage - 1)} disabled={currentPage === 1} />
                        {[...Array(totalPages)].map((_, index) => (
                            <Pagination.Item key={index + 1} active={index + 1 === currentPage} onClick={() => onPageChange && onPageChange(index + 1)}>
                                {index + 1}
                            </Pagination.Item>
                        ))}
                        <Pagination.Next onClick={() => onPageChange && onPageChange(currentPage + 1)} disabled={currentPage === totalPages} />
                    </Pagination>
                </Stack>
            )}
        </Stack>
    );
};

export default RoomListDisplay;