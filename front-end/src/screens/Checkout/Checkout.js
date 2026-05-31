import { useState, useEffect } from "react";
import { Container, Row, Col, Card, Stack, Form, Button, InputGroup, Badge, Image } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import BackButton from "../../components/BackButton";
import toast from "react-hot-toast";

const Checkout = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const bookingSummary = location.state?.bookingSummary || {
        roomId: 101,
        roomName: "P101",
        name: "Standard Room",
        pricePerNight: 500000,
        dayStart: "2026-05-10",
        dayEnd: "2026-05-12",
        totalNights: 2,
        roomAmount: 1000000,
        taxAmount: 100000,
        depositAmount: 500000,
        totalAmount: 1100000
    };

    const [paymentMethod, setPaymentMethod] = useState("QR_BANK");
    const [voucherCode, setVoucherCode] = useState("");
    const [discountMoney, setDiscountMoney] = useState(0);
    const [isVoucherApplied, setIsVoucherApplied] = useState(false);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const user = localStorage.getItem("user");
        if (!user) {
            toast.error("Vui lòng đăng nhập hệ thống để hoàn tất quy trình thanh toán!");
            navigate("/login");
        }
    }, [navigate]);

    const handleApplyVoucher = (e) => {
        e.preventDefault();
        if (voucherCode.toUpperCase() === "OUHOTEL10") {
            const discount = bookingSummary.roomAmount * 0.1;
            setDiscountMoney(discount);
            setIsVoucherApplied(true);
            toast.success("Áp dụng mã giảm giá thành công! Bạn được giảm " + formatVND(discount));
        } else {
            toast.error("Mã giảm giá không tồn tại hoặc đã hết hạn sử dụng!");
        }
    };

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const formatDate = (dateStr) => {
        if (!dateStr) return "";
        const [year, month, day] = dateStr.split("-");
        return `${day}/${month}/${year}`;
    };

    const handleConfirmPayment = async () => {
        setLoading(true);
        const userStr = localStorage.getItem("user");
        const user = userStr ? JSON.parse(userStr) : null;

        const finalAmount = bookingSummary.totalAmount - discountMoney;

        const payload = {
            roomId: bookingSummary.roomId,
            roomName: bookingSummary.roomName,
            pricePerNight: bookingSummary.pricePerNight,
            dayStart: bookingSummary.dayStart,
            dayEnd: bookingSummary.dayEnd,
            userName: user ? user.userName : "Ẩn danh",
            voucherDiscountMoney: discountMoney,
            depositAmount: bookingSummary.depositAmount,
            totalAmount: finalAmount,
            bookingSource: 1,
            note: "Khách thanh toán trực tuyến qua phương thức: " + paymentMethod
        };

        try {
            const res = await fetch("/api/bookings", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });
            const result = await res.json();
            if (!res.ok) throw new Error(result.message);

            const createdBookingId = result.data.roomBookingId;

            if (paymentMethod === "VNPAY") {
                toast.success("Đang chuyển hướng sang cổng VNPay...");

                const vnpRes = await fetch("/api/payment/create", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        amount: finalAmount,
                        bookingId: createdBookingId
                    })
                });
                const vnpData = await vnpRes.json();

                if (vnpData.url) {
                    navigate(vnpData.url);
                    // window.location.href = vnpData.url;
                }
            } else {
                toast.success(result.message);
                navigate(`/receipt/${createdBookingId}`);
            }

        } catch (error) {
            toast.error(error.message || "Xảy ra sự cố trong quá trình khởi tạo đơn phòng.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <BackButton className="mb-4" label="Quay lại" />

            <Stack className="mb-4">
                <Card.Title as="h2" className="text-gold fw-bold luxury-title">Xác Nhận & Thanh Toán</Card.Title>
                <Stack as="span" className="text-white-50 small">Vui lòng kiểm tra lại thông tin lưu trú và lựa chọn phương thức thanh toán phù hợp</Stack>
            </Stack>

            <Row className="g-5">
                <Col lg={7}>
                    <Card className="glass-card-static border-0 shadow-lg p-4 mb-4">
                        <Card.Body className="p-0">
                            <Card.Title as="h5" className="text-white fw-bold mb-4">
                                <Stack as="i" className="bi bi-wallet2 text-gold me-2"></Stack>Chọn phương thức thanh toán
                            </Card.Title>

                            <Stack gap={3} className="mb-4">
                                <Card
                                    className={`checkout-payment-card p-3 ${paymentMethod === "QR_BANK" ? "active" : ""}`}
                                    onClick={() => setPaymentMethod("QR_BANK")}
                                >
                                    <Stack direction="horizontal" className="justify-content-between align-items-center">
                                        <Stack direction="horizontal" gap={3} className="align-items-center">
                                            <Stack as="i" className="bi bi-qr-code text-gold fs-3"></Stack>
                                            <Stack>
                                                <Stack as="span" className="text-white fw-bold">Chuyển khoản Ngân hàng điện tử (Quét mã QR)</Stack>
                                            </Stack>
                                        </Stack>
                                        <Form.Check
                                            type="radio"
                                            checked={paymentMethod === "QR_BANK"}
                                            onChange={() => setPaymentMethod("QR_BANK")}
                                            className="custom-gold-radio"
                                        />
                                    </Stack>
                                </Card>

                                <Card
                                    className={`checkout-payment-card p-3 ${paymentMethod === "CASH" ? "active" : ""}`}
                                    onClick={() => setPaymentMethod("CASH")}
                                >
                                    <Stack direction="horizontal" className="justify-content-between align-items-center">
                                        <Stack direction="horizontal" gap={3} className="align-items-center">
                                            <Stack as="i" className="bi bi-cash-coin text-gold fs-3"></Stack>
                                            <Stack>
                                                <Stack as="span" className="text-white fw-bold">Thanh toán bằng tiền mặt tại quầy lễ tân</Stack>
                                            </Stack>
                                        </Stack>
                                        <Form.Check
                                            type="radio"
                                            checked={paymentMethod === "CASH"}
                                            onChange={() => setPaymentMethod("CASH")}
                                            className="custom-gold-radio"
                                        />
                                    </Stack>
                                </Card>
                                <Card
                                    className={`checkout-payment-card p-3 ${paymentMethod === "VNPAY" ? "active" : ""}`}
                                    onClick={() => setPaymentMethod("VNPAY")}
                                >
                                    <Stack direction="horizontal" className="justify-content-between align-items-center">
                                        <Stack direction="horizontal" gap={3} className="align-items-center">
                                            <Image
                                                src="https://cdn.haitrieu.com/wp-content/uploads/2022/10/Logo-VNPAY-QR-1.png"
                                                style={{ height: "30px", objectFit: "contain", backgroundColor: "white", padding: "2px", borderRadius: "4px" }}
                                                alt="VNPay"
                                            />
                                            <Stack>
                                                <Stack as="span" className="text-white fw-bold">Thanh toán trực tuyến qua VNPay</Stack>
                                            </Stack>
                                        </Stack>
                                        <Form.Check
                                            type="radio"
                                            checked={paymentMethod === "VNPAY"}
                                            onChange={() => setPaymentMethod("VNPAY")}
                                            className="custom-gold-radio"
                                        />
                                    </Stack>
                                </Card>
                            </Stack>

                            {paymentMethod === "QR_BANK" && (
                                <Stack className="align-items-center text-center p-4 checkout-receipt-panel border border-secondary">
                                    <Stack className="checkout-qr-wrapper mb-3 justify-content-center align-items-center">
                                        <Image
                                            src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=OUHotelPayment"
                                            alt="Mã QR Thanh Toán"
                                        />
                                    </Stack>
                                    <Stack as="span" className="text-gold fw-bold mb-1">Mã QR Thanh Toán Tự Động</Stack>
                                    <Stack as="span" className="text-white-50 small">Nội dung chuyển khoản: <Stack as="strong" className="text-white font-monospace">Tên phòng - Tiền cọc - Họ và tên khách hàng - Thời gian lưu trú</Stack></Stack>
                                </Stack>
                            )}


                        </Card.Body>
                    </Card>
                </Col>

                <Col lg={5}>
                    <Card className="glass-card-static border-0 shadow-lg p-4">
                        <Card.Body className="p-0">
                            <Card.Title as="h5" className="text-white fw-bold mb-4">
                                <Stack as="i" className="bi bi-receipt text-gold me-2"></Stack>Chi tiết hóa đơn đặt phòng
                            </Card.Title>

                            <Stack gap={2} className="mb-4 p-3 checkout-receipt-panel">
                                <Stack direction="horizontal" className="justify-content-between">
                                    <Stack as="span" className="text-white fw-bold">{bookingSummary.name}</Stack>
                                    <Badge bg="warning" text="dark" className="fw-bold">{bookingSummary.roomName}</Badge>
                                </Stack>
                                <Stack className="divider-1px border-top border-secondary opacity-20 my-2 w-100"></Stack>
                                <Stack as="span" className="text-white-50 small">Thời gian lưu trú: {formatDate(bookingSummary.dayStart)} - {formatDate(bookingSummary.dayEnd)} ({bookingSummary.totalNights} đêm)</Stack>
                                <Stack as="span" className="text-white-50 small">Đơn giá phòng: {formatVND(bookingSummary.pricePerNight)} / đêm</Stack>
                            </Stack>

                            <Form onSubmit={handleApplyVoucher} className="mb-4">
                                <Form.Group>
                                    <Form.Label className="text-white-50 small mb-1">Mã ưu đãi giảm giá (Voucher)</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            disabled={isVoucherApplied}
                                            className="custom-dark-input bg-transparent text-white border-secondary py-2 input-no-border-right"
                                            placeholder="Nhập mã ưu đãi của bạn..."
                                            value={voucherCode}
                                            onChange={(e) => setVoucherCode(e.target.value)}
                                        />
                                        <Button variant="outline-warning" type="submit" disabled={isVoucherApplied} className="fw-bold px-4">
                                            Áp dụng
                                        </Button>
                                    </InputGroup>
                                </Form.Group>
                            </Form>

                            <Stack gap={3} className="mb-4">
                                <Stack direction="horizontal" className="justify-content-between text-white-50 small">
                                    <Stack as="span">Tổng tiền phòng vật lý:</Stack>
                                    <Stack as="span" className="text-white">{formatVND(bookingSummary.roomAmount)}</Stack>
                                </Stack>
                                <Stack direction="horizontal" className="justify-content-between text-white-50 small">
                                    <Stack as="span">Thuế GTGT và phí dịch vụ (10%):</Stack>
                                    <Stack as="span" className="text-white">{formatVND(bookingSummary.taxAmount)}</Stack>
                                </Stack>
                                {isVoucherApplied && (
                                    <Stack direction="horizontal" className="justify-content-between small">
                                        <Stack as="span" className="text-white-50">Mã ưu đãi khẩu trừ:</Stack>
                                        <Stack as="span" className="text-danger">-{formatVND(discountMoney)}</Stack>
                                    </Stack>
                                )}
                                <Stack className="divider-1px border-top border-secondary opacity-50 my-1 w-100"></Stack>
                                <Stack direction="horizontal" className="justify-content-between align-items-end">
                                    <Stack as="span" className="text-white fw-bold">Tổng chi phí thanh toán:</Stack>
                                    <Stack as="span" className="text-gold fw-bold fs-4">{formatVND(bookingSummary.totalAmount - discountMoney)}</Stack>
                                </Stack>
                                <Stack direction="horizontal" className="justify-content-between text-white-50 small mt-1">
                                    <Stack as="span">Tổng tiền cọc trước:</Stack>
                                    <Stack as="span" className="text-warning fw-bold">{formatVND(bookingSummary.depositAmount)}</Stack>
                                </Stack>
                            </Stack>

                            <Button
                                variant="warning"
                                disabled={loading}
                                onClick={handleConfirmPayment}
                                className="w-100 fw-bold py-3 rounded-pill text-dark shadow btn-luxury-glow"
                            >
                                {loading ? "ĐANG XỬ LÝ GIAO DỊCH..." : "XÁC NHẬN ĐẶT PHÒNG"}
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Checkout;