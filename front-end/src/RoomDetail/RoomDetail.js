import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Badge, Button, Card, Col, Container, Row, Stack } from "react-bootstrap";
import MySpinner from "../components/MySpinner"; 

const RoomDetail = () => {
    const { id } = useParams(); 
    const navigate = useNavigate();
    
    const [room, setRoom] = useState(null);
    const [loading, setLoading] = useState(true);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    useEffect(() => {
        setLoading(true);
        fetch(`/api/rooms/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("Không tìm thấy dữ liệu phòng");
                return res.json();
            })
            .then(data => {
                setRoom(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Lỗi hệ thống khi tải chi tiết phòng:", error);
                setRoom(null);
                setLoading(false);
            });
    }, [id]);

    if (loading) return <MySpinner />;
    
    if (!room) return (
        <Container className="text-center my-5 py-5">
            <Stack as="h3" className="text-white-50">Phòng không tồn tại hoặc đã bị gỡ khỏi hệ thống.</Stack>
            <Button variant="outline-warning" className="mt-3 rounded-pill" onClick={() => navigate('/rooms')}>
                Quay lại danh sách
            </Button>
        </Container>
    );

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            
            <Stack 
                direction="horizontal" 
                className="mb-4 text-white-50 align-items-center" 
                style={{ cursor: 'pointer', width: 'fit-content' }}
                onClick={() => navigate(-1)}
            >
                <Stack as="i" className="bi bi-arrow-left text-gold me-2 fs-5"></Stack>
                <Stack as="span" className="hover-text-gold">Quay lại danh sách phòng</Stack>
            </Stack>

            <Row className="g-5">
                <Col lg={8}>
                    <Card className="glass-card-static border-0 shadow-lg mb-4 overflow-hidden">
                        <Card.Img src={room.avatarUrl} style={{ objectFit: 'cover', height: '500px', width: '100%' }} />
                    </Card>

                    <Row className="g-3 mb-5">
                        {room.images?.map((img, idx) => (
                            <Col xs={6} md={3} key={idx}>
                                <Card className="glass-card-static border-0 overflow-hidden shadow-sm">
                                    <Card.Img src={img} style={{ objectFit: 'cover', height: '120px' }} />
                                </Card>
                            </Col>
                        ))}
                    </Row>

                    <Stack className="mb-5 pb-4 border-bottom border-secondary">
                        <Stack direction="horizontal" className="align-items-center mb-3 flex-wrap gap-2">
                            <Stack as="h1" className="text-gold luxury-title fw-bold mb-0 me-2">{room.name}</Stack>
                            <Badge bg="warning" text="dark" className="fs-6 px-3 py-2 fw-bold">{room.roomName}</Badge>
                        </Stack>
                        
                        <Stack direction="horizontal" gap={4} className="text-white-50 mb-4 flex-wrap">
                            <Stack direction="horizontal">
                                <Stack as="i" className="bi bi-person me-2 text-gold"></Stack> 
                                Tối đa {room.capacity} người lớn
                            </Stack>
                            <Stack direction="horizontal">
                                <Stack as="i" className="bi bi-arrows-fullscreen me-2 text-gold"></Stack> 
                                Diện tích {room.size}
                            </Stack>
                            <Stack direction="horizontal">
                                <Stack as="i" className="bi bi-door-open me-2 text-gold"></Stack> 
                                Cấu hình {room.bedType}
                            </Stack>
                        </Stack>
                        
                        <Card.Text as={Stack} className="text-white fs-6 lh-lg">
                            {room.description}
                        </Card.Text>
                    </Stack>
                    <Stack className="mb-5">
                        <Stack as="h4" className="text-gold fw-bold mb-4 luxury-title">Tiện ích phòng cao cấp</Stack>
                        <Row className="g-3">
                            {room.amenities?.map((amenity, idx) => (
                                <Col md={4} sm={6} key={idx}>
                                    <Stack direction="horizontal" className="text-white-50 align-items-center">
                                        <Stack as="i" className="bi bi-check-circle-fill text-success me-2"></Stack>
                                        <Stack as="span">{amenity}</Stack>
                                    </Stack>
                                </Col>
                            ))}
                        </Row>
                    </Stack>
                </Col>
                <Col lg={4}>
                    <Stack className="position-sticky" style={{ top: '100px', zIndex: 10 }}>
                        <Card className="glass-card-static border-0 shadow-lg">
                            <Card.Body className="p-4 p-xl-5">
                                <Stack direction="horizontal" className="justify-content-between align-items-end mb-4 pb-3 border-bottom border-secondary">
                                    <Stack>
                                        <Card.Text as="small" className="text-white-50 mb-1 d-block">Chi phí / Đêm</Card.Text>
                                        <Stack as="h3" className="text-gold fw-bold mb-0">{formatVND(room.price)}</Stack>
                                    </Stack>
                                    <Badge bg="success" className="p-2 px-3 rounded-pill">Trống lịch</Badge>
                                </Stack>

                                <Stack className="mb-4">
                                    <Card.Text as="small" className="text-white-50 mb-2">Khung giờ quy định</Card.Text>
                                    <Stack className="bg-dark p-3 rounded border border-secondary mb-3 text-white-50 small" gap={2}>
                                        <Stack direction="horizontal" className="justify-content-between">
                                            <Stack direction="horizontal" className="align-items-center">
                                                <Stack as="i" className="bi bi-box-arrow-in-right me-2 text-gold"></Stack>
                                                <Stack as="span">Nhận phòng (Check-in)</Stack>
                                            </Stack>
                                            <Stack as="span" className="text-white fw-bold">14:00</Stack>
                                        </Stack>
                                        <Stack direction="horizontal" className="justify-content-between">
                                            <Stack direction="horizontal" className="align-items-center">
                                                <Stack as="i" className="bi bi-box-arrow-right me-2 text-gold"></Stack>
                                                <Stack as="span">Trả phòng (Check-out)</Stack>
                                            </Stack>
                                            <Stack as="span" className="text-white fw-bold">12:00</Stack>
                                        </Stack>
                                    </Stack>
                                </Stack>

                                <Button variant="warning" size="lg" className="w-100 fw-bold rounded-pill text-dark shadow-lg btn-luxury-glow">
                                    ĐẶT PHÒNG NGAY
                                </Button>
                                
                            </Card.Body>
                        </Card>
                    </Stack>
                </Col>
            </Row>
        </Container>
    );
};

export default RoomDetail;