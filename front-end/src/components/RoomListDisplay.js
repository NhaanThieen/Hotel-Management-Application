import { Button, Card, Col, Row, Stack, Pagination, Badge } from "react-bootstrap";
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
                    <Card.Img variant="top" src={room.avatarUrl} className="room-img" style={{ objectFit: 'cover', height: '240px' }} />
                </Stack>
                <Card.Body className="d-flex flex-column p-4">
                    <Card.Title as={Stack} direction="horizontal" className="text-white fw-bold mb-3 fs-4 align-items-center flex-wrap gap-2">
                        <span>{room.name}</span>
                        <Badge bg="warning" text="dark" className="fs-6 px-2 py-1">{room.roomName}</Badge>
                    </Card.Title>
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
                            <Card.Title as={Stack} direction="horizontal" className="text-white fw-bold fs-4 align-items-center mb-2 flex-wrap gap-2">
                                <span>{room.name}</span>
                                <Badge bg="warning" text="dark" className="fs-6 px-2 py-1">{room.roomName}</Badge>
                            </Card.Title>
                            <Card.Text as={Stack} direction="horizontal" className="text-white-50 small mb-3 align-items-center flex-wrap gap-2">
                                <Stack direction="horizontal" className="align-items-center">
                                    <i className="bi bi-person me-1"></i> Tối đa {room.capacity || 2} người lớn
                                </Stack>
                                {room.size && (
                                    <>
                                        <span className="mx-1 text-secondary">|</span>
                                        <Stack direction="horizontal" className="align-items-center">
                                            <i className="bi bi-arrows-fullscreen me-1" style={{ fontSize: '0.85rem' }}></i> Diện tích {room.size}
                                        </Stack>
                                    </>
                                )}
                            </Card.Text>
                            <Stack
                                direction="horizontal"
                                className="text-gold text-decoration-none small align-items-center"
                                style={{ cursor: 'pointer', width: 'fit-content' }}
                                onClick={() => navigate(`/rooms/${room.id}`)}
                            >
                                <span className="fw-semibold">Xem chi tiết phòng</span>
                                <i className="bi bi-chevron-right ms-1" style={{ fontSize: '0.75rem' }}></i>
                            </Stack>
                        </Stack>

                        <Stack className="mt-auto pt-3 border-top border-secondary">
                            <Stack direction="horizontal" className="justify-content-between align-items-center">
                                <Stack direction="horizontal" className="w-100 justify-content-between align-items-center">
                                    <Stack>
                                        <span className="text-white-50 small d-block">Giá linh hoạt</span>
                                        <Card.Text as={Stack} className="text-gold fw-bold mb-0 fs-5">{formatVND(room.price)}</Card.Text>
                                    </Stack>
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