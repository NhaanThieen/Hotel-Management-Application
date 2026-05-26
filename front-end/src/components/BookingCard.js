import React from "react";
import { Card, Stack, Badge, Row, Col, Button } from "react-bootstrap";

const BookingCard = React.memo(({ item, formatVND, formatDate, onNavigate }) => {
    return (
        <Card className="glass-card-static booking-card border-0 shadow-lg p-4">
            <Card.Body className="p-0">
                <Stack direction="horizontal" className="justify-content-between align-items-center mb-3 flex-wrap gap-2 pb-3 border-bottom border-secondary">
                    <Stack>
                        <Card.Title as="h5" className="text-gold fw-bold mb-1">{item.title}</Card.Title>
                        <Stack direction="horizontal" gap={3} className="text-white-50 small flex-wrap">
                            <Stack as="span">{item.subText}</Stack>
                            {item.type === "ROOM" && item.raw.staffName && (
                                <Stack as="span">Nhân viên tiếp đón: {item.raw.staffName}</Stack>
                            )}
                        </Stack>
                    </Stack>
                    <Badge bg={item.badgeBg} text="dark" className="px-3 py-2 rounded-pill booking-status-badge text-uppercase">
                        {item.badgeText}
                    </Badge>
                </Stack>

                <Row className="g-4 align-items-center">
                    <Col lg={8}>
                        <Stack gap={3}>
                            {/* Danh sách Phòng */}
                            {item.type === "ROOM" && item.raw.details?.map((detail) => (
                                <Stack key={detail.roomBookingDetailId} className="booking-detail-box p-3">
                                    <Stack direction="horizontal" className="justify-content-between align-items-center mb-2 flex-wrap gap-2">
                                        <Card.Title as="h5" className="text-white fw-bold mb-0">Thông tin phòng: {detail.roomName}</Card.Title>
                                        <Stack as="span" className="text-gold fw-bold">{formatVND(detail.price)} / đêm</Stack>
                                    </Stack>
                                    <Row className="g-2 text-white-50 small">
                                        <Col sm={6}>
                                            <Stack as="span">Nhận phòng: {formatDate(item.raw.dayStart)} ({item.raw.timeStart})</Stack>
                                        </Col>
                                        <Col sm={6}>
                                            <Stack as="span">Trả phòng: {formatDate(item.raw.dayEnd)} ({item.raw.timeEnd})</Stack>
                                        </Col>
                                    </Row>
                                </Stack>
                            ))}

                            {/* Danh sách Dịch vụ */}
                            {item.type === "SERVICE" && item.raw.services?.map((serv, sIdx) => (
                                <Stack key={sIdx} direction="horizontal" className="justify-content-between booking-detail-box p-3 align-items-center">
                                    <Stack>
                                        <Card.Title as="h6" className="text-white fw-bold mb-1">{serv.serviceName}</Card.Title>
                                        <Stack as="span" className="text-white-50 small">Số lượng: {serv.quantity} đơn vị</Stack>
                                    </Stack>
                                    <Stack as="span" className="text-gold fw-bold">{formatVND(serv.unitPriceAtUse)}</Stack>
                                </Stack>
                            ))}
                        </Stack>
                    </Col>
                    
                    <Col lg={4} className="border-start border-secondary ps-lg-4 text-end">
                        <Stack gap={2} className="align-items-lg-end">
                            {item.type === "ROOM" && (
                                <Stack className="w-100">
                                    <Stack direction="horizontal" className="justify-content-between justify-content-lg-end w-100 gap-3 text-white-50 small">
                                        <Stack as="span">Khấu trừ giảm giá:</Stack>
                                        <Stack as="span" className="text-danger">-{formatVND(item.raw.voucherDiscountMoney || 0)}</Stack>
                                    </Stack>
                                    <Stack direction="horizontal" className="justify-content-between justify-content-lg-end w-100 gap-3 text-white-50 small">
                                        <Stack as="span">Số tiền đặt cọc:</Stack>
                                        <Stack as="span" className="text-white fw-bold">{formatVND(item.raw.depositAmount || 0)}</Stack>
                                    </Stack>
                                </Stack>
                            )}
                            <Stack direction="horizontal" className="justify-content-between justify-content-lg-end w-100 gap-3 align-items-end">
                                <Stack as="span" className="text-white fw-bold mb-1">Tổng tiền thanh toán:</Stack>
                                <Stack as="span" className="text-gold fw-bold fs-3">{formatVND(item.amount)}</Stack>
                            </Stack>
                            <Button 
                                variant="outline-warning" 
                                className="w-100 fw-bold rounded-pill mt-3 btn-room-dark"
                                onClick={() => onNavigate(item.id)}
                            >
                                <Stack as="i" className="bi bi-file-earmark-text me-2"></Stack>Xem hóa đơn
                            </Button>
                        </Stack>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    );
});

export default BookingCard;