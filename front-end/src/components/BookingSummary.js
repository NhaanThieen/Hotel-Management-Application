import { useState } from "react";
import { Button, Card, Stack, Badge, Form } from "react-bootstrap";
import BookingDateRange from "./BookingDateRange";
import toast from "react-hot-toast";

const BookingSummary = ({
    room,
    buttonText = "Tiếp tục thanh toán",
    onAction,
    defaultDayStart = "",
    defaultDayEnd = "",
    defaultTimeStart = "14:00",
    defaultTimeEnd = "12:00"
}) => {
    const [dayStart, setDayStart] = useState(defaultDayStart ? new Date(defaultDayStart) : null);
    const [dayEnd, setDayEnd] = useState(defaultDayEnd ? new Date(defaultDayEnd) : null);

    const [timeStart, setTimeStart] = useState(defaultTimeStart);
    const [timeEnd, setTimeEnd] = useState(defaultTimeEnd);
    const [note, setNote] = useState("");

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const formatDateForDB = (dateObj) => {
        if (!dateObj) return "";
        const year = dateObj.getFullYear();
        const month = String(dateObj.getMonth() + 1).padStart(2, '0');
        const day = String(dateObj.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    let checkInDate = null;
    let checkOutDate = null;
    let totalNights = 0;
    let totalHours = 0;
    let roomAmount = 0;
    let taxAmount = 0;
    let grandTotal = 0;

    if (dayStart && dayEnd && timeStart && timeEnd && room) {
        checkInDate = new Date(`${formatDateForDB(dayStart)}T${timeStart}:00`);
        checkOutDate = new Date(`${formatDateForDB(dayEnd)}T${timeEnd}:00`);

        if (checkOutDate > checkInDate) {
            let current12h = new Date(checkInDate);
            current12h.setHours(12, 0, 0, 0);
            
            if (checkInDate >= current12h) {
                current12h.setDate(current12h.getDate() + 1);
            }
            
            while (current12h <= checkOutDate) {
                totalNights++;
                current12h.setDate(current12h.getDate() + 1);
            }

            const diffMs = checkOutDate.getTime() - checkInDate.getTime();
            const diffMins = Math.floor(diffMs / 60000);
            totalHours = Math.floor(diffMins / 60);
            const remainderMins = diffMins % 60;
            
            if (remainderMins > 15) {
                totalHours++;
            }

            const price = room.price || 0;
            const priceOvernight = price * 0.7;
            const priceBlock2 = price * 0.3;
            const priceNextHour = price * 0.1;

            if (totalNights === 0) {
                let hourlyTotal = 0;
                if (totalHours <= 2) {
                    hourlyTotal = priceBlock2;
                } else {
                    hourlyTotal = priceBlock2 + ((totalHours - 2) * priceNextHour);
                }
                
                roomAmount = Math.min(price, priceOvernight, hourlyTotal);
            } else {
                let excessHours = Math.max(0, totalHours - (24 * totalNights));
                let excessCost = excessHours * priceNextHour;
                
                roomAmount = (totalNights * price) + Math.min(excessCost, price);
            }

            taxAmount = roomAmount * 0.1;
            grandTotal = roomAmount + taxAmount;
        }
    }

    const handleActionClick = () => {
        if (!dayStart || !dayEnd) {
            toast.error("Vui lòng chọn đầy đủ ngày nhận và trả phòng!");
            return;
        }
        
        if (checkOutDate <= checkInDate) {
            toast.error("Thời gian trả phòng phải sau thời gian nhận phòng!");
            return;
        }

        const bookingPayload = {
            roomId: room.id,
            roomName: room.roomName,
            dayStart: formatDateForDB(dayStart),
            dayEnd: formatDateForDB(dayEnd),
            timeStart: timeStart,
            timeEnd: timeEnd,
            note: note,
            bookingSource: 1, 
            
            name: room.name,
            totalNights: totalNights,
            roomAmount: roomAmount, 
            taxAmount: taxAmount,
            totalAmount: grandTotal,
            pricePerNight: room.price
        };

        if (onAction) {
            onAction(bookingPayload);
        }
    };

    return (
        <Stack className="position-sticky" style={{ top: '100px', zIndex: 10 }}>
            <Card className="glass-card-static border-0 shadow-lg">
                <Card.Body className="p-4">

                    <Stack direction="horizontal" className="justify-content-between align-items-center mb-3">
                        <Card.Title as="h5" className="text-gold fw-bold mb-0 font-italic" style={{ fontFamily: 'serif' }}>OU Hotel</Card.Title>
                    </Stack>

                    <hr className="border-secondary opacity-50 mb-4" />

                    {room ? (
                        <Stack >
                            <Stack className="glass-card-static-light p-3 rounded border border-secondary mb-4">

                                <BookingDateRange
                                    checkInDate={dayStart}
                                    setCheckInDate={setDayStart}
                                    checkOutDate={dayEnd}
                                    setCheckOutDate={setDayEnd}
                                    containerClassName="w-100 mb-3"
                                    itemClassName="w-50 px-1"
                                    labelClassName="text-white small mb-1 d-block"
                                    inputClassName=" custom-dark-input form-control form-control-sm bg-transparent text-white border-secondary w-100"
                                />

                                <Stack direction="horizontal" className="mb-3 w-100">
                                    <Stack className="w-50 px-1">
                                        <Form.Label className="text-white-50 small mb-1">Giờ nhận</Form.Label>
                                        <Form.Control
                                            type="time" size="sm" className=" custom-dark-input bg-transparent text-white border-secondary"
                                            value={timeStart} onChange={(e) => setTimeStart(e.target.value)}
                                        />
                                    </Stack>
                                    <Stack className="w-50 px-1">
                                        <Form.Label className="text-white-50 small mb-1">Giờ trả</Form.Label>
                                        <Form.Control
                                            type="time" size="sm" className=" custom-dark-input bg-transparent text-white border-secondary"
                                            value={timeEnd} onChange={(e) => setTimeEnd(e.target.value)}
                                        />
                                    </Stack>
                                </Stack>

                                <Stack className="px-1">
                                    <Form.Label className="text-white-50 small mb-1">Ghi chú đặc biệt</Form.Label>
                                    <Form.Control
                                        as="textarea" rows={2} size="sm" className=" custom-dark-input bg-transparent text-white border-secondary"
                                        placeholder="Bạn có yêu cầu gì thêm không?"
                                        value={note} onChange={(e) => setNote(e.target.value)}
                                    />
                                </Stack>

                            </Stack>

                            <Stack direction="horizontal" className="mb-4 align-items-start">
                                <Card.Img src={room.avatarUrl} style={{ width: '65px', height: '65px', objectFit: 'cover' }} className="rounded me-3 shadow-sm border border-secondary" />
                                <Stack>
                                    <Stack direction="horizontal" className="align-items-center mb-1 flex-wrap gap-2">
                                        <span className="text-white fw-bold">{room.name}</span>
                                        {room.roomName && <Badge bg="warning" text="dark" className="px-2">{room.roomName}</Badge>}
                                    </Stack>
                                    
                                    <span className="text-white-50 small mb-1">
                                        1 phòng, <span className="text-gold fw-bold">
                                            {totalNights === 0 ? `${totalHours} giờ` : `${totalNights} đêm`}
                                        </span>
                                    </span>

                                    <span className="text-gold fw-bold">{formatVND(room.price)} <small className="text-white-50 fw-normal">/ đêm gốc</small></span>
                                </Stack>
                            </Stack>

                            <Stack direction="horizontal" className="justify-content-between text-white-50 small mb-2">
                                <span>Tiền phòng {totalNights === 0 ? `(Lưu trú theo giờ)` : `(${totalNights} đêm)`}</span>
                                <span>{formatVND(roomAmount)}</span>
                            </Stack>
                            
                            <Stack direction="horizontal" className="justify-content-between text-white-50 small mb-3">
                                <span>Thuế & Phí dịch vụ (10%)</span>
                                <span>{formatVND(taxAmount)}</span>
                            </Stack>

                            <hr className="border-secondary opacity-50 mb-3" />

                            <Stack direction="horizontal" className="justify-content-between align-items-end mb-4">
                                <span className="text-white fw-bold fs-5">Tổng cộng</span>
                                <span className="text-gold fw-bold fs-4">{formatVND(grandTotal)}</span>
                            </Stack>

                            <Button
                                variant="warning" size="lg" className="w-100 fw-bold rounded-pill text-dark shadow btn-luxury-glow"
                                onClick={handleActionClick}
                            >
                                {buttonText}
                            </Button>
                        </Stack>
                    ) : (
                        <Stack className="text-center py-5 align-items-center">
                            <i className="bi bi-check2-square text-white-50 mb-3" style={{ fontSize: '3rem' }}></i>
                            <span className="text-white-50">Vui lòng chọn phòng để xem tổng quan đơn đặt.</span>
                        </Stack>
                    )}

                </Card.Body>
            </Card>
        </Stack>
    );
};

export default BookingSummary;