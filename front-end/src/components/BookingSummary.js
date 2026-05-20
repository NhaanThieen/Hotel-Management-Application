import { Button, Card, Stack } from "react-bootstrap";

const BookingSummary = ({ selectedRoom }) => {
    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    return (
        <Stack className="position-sticky" style={{ top: '100px', zIndex: 10 }}>
            <Card className="glass-card border-0 shadow-lg">
                <Card.Body className="p-4">
                    <Card.Title as={Stack} className="text-gold luxury-title fw-bold mb-2 fs-5">OU Hotel</Card.Title>
                    <Card.Text as={Stack} direction="horizontal" className="text-white-50 small mb-4 pb-3 border-bottom border-secondary align-items-center">
                        <Stack as="i" className="bi bi-clock me-2"></Stack> Nhận phòng: 14:00 | Trả phòng: 12:00
                    </Card.Text>

                    {selectedRoom ? (
                        <Stack>
                            <Stack direction="horizontal" className="mb-4">
                                <Card.Img src={selectedRoom.avatarUrl} style={{ width: '70px', height: '70px', objectFit: 'cover' }} className="rounded me-3 shadow-sm" />
                                <Stack>
                                    <Card.Title as={Stack} className="text-white fw-bold mb-1 fs-6">{selectedRoom.name}</Card.Title>
                                    <Card.Text as={Stack} className="text-white-50 small mb-1">1 phòng, 1 đêm</Card.Text>
                                    <Card.Text as={Stack} className="text-gold fw-bold mb-0">{formatVND(selectedRoom.price)}</Card.Text>
                                </Stack>
                            </Stack>
                            <Stack direction="horizontal" className="justify-content-between text-white-50 small mb-2">
                                <Stack as="span">Thuế & Phí dịch vụ (10%)</Stack>
                                <Stack as="span">{formatVND(selectedRoom.price * 0.1)}</Stack>
                            </Stack>
                            <Stack direction="horizontal" className="justify-content-between align-items-end mb-4 pt-3 border-top border-secondary">
                                <Stack as="span" className="text-white fw-bold fs-5">Tổng cộng</Stack>
                                <Stack as="span" className="text-gold fw-bold fs-4">{formatVND(selectedRoom.price * 1.1)}</Stack>
                            </Stack>
                            <Button variant="warning" size="lg" className="w-100 fw-bold rounded-pill text-dark shadow">
                                Tiếp tục thanh toán
                            </Button>
                        </Stack>
                    ) : (
                        <Stack className="text-center py-5 align-items-center">
                            <Stack as="i" className="bi bi-check2-square text-white-50" style={{ fontSize: '3rem' }}></Stack>
                            <Card.Text as={Stack} className="text-white-50 mt-3 mb-0">Vui lòng chọn phòng ở danh sách bên trái để xem tổng quan đơn đặt phòng.</Card.Text>
                        </Stack>
                    )}
                </Card.Body>
            </Card>
        </Stack>
    );
};

export default BookingSummary;