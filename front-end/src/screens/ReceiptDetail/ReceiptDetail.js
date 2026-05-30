import { useEffect, useState } from "react";
import { Container, Row, Col, Card, Stack, Badge, Button } from "react-bootstrap";
import { useParams, useNavigate } from "react-router-dom";
import MySpinner from "../../components/MySpinner";
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import toast from "react-hot-toast";

const ReceiptDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [receipt, setReceipt] = useState(null);
    const [loading, setLoading] = useState(true);


    const handleExportPDF = async () => {
    const element = document.getElementById("receipt-content"); 
    const canvas = await html2canvas(element, { 
        scale: 2, 
        backgroundColor: '#121212' // Ép màu nền tối
    });
    const imgData = canvas.toDataURL('image/png');
    
    // Tạo PDF khổ A4
    const pdf = new jsPDF('p', 'mm', 'a4');
    const imgWidth = 210; 
    const imgHeight = (canvas.height * imgWidth) / canvas.width;
    
    pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
    pdf.save(`Hoa_Don_${id}.pdf`);
};


    useEffect(() => {
        fetch(`/api/receipts/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("Biên lai không tồn tại trên hệ thống.");
                return res.json();
            })
            .then(data => {
                setReceipt(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                toast.error("Không tìm thấy thông tin biên lai này!");
                navigate("/my-bookings");
            });
    }, [id, navigate]);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    if (loading) return <MySpinner />;

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <Row className="justify-content-center">
                <Col xl={8} lg={10}>
                    <Card className="receipt-paper border-0 shadow-lg p-4 p-md-5 overflow-hidden">
                        <Stack className="bi bi-shield-check receipt-watermark-icon" as="i"></Stack>

                        <Card.Body className="p-0">
                            <Stack className="align-items-center text-center mb-4 pb-4 receipt-header-box w-100">
                                <Card.Title as="h2" className="text-gold fw-bold luxury-title mb-1">Thông tin hóa đơn</Card.Title>
                                <Stack as="span" className="text-white-50 small">Địa chỉ: 97 Võ Văn Tần, Phường Xuân Hòa, TP. Hồ Chí Minh</Stack>
                                <Stack as="span" className="text-white-50 small">Hotline: +84 (0) 28 3930 0077</Stack>
                            </Stack>

                            <Row className="g-4 mb-5 text-white-50 small">
                                <Col md={6}>
                                    <Stack gap={2}>
                                        <Card.Title as="h6" className="text-gold fw-bold text-uppercase mb-1 tracking-wider">Thông tin khách hàng</Card.Title>
                                        <Stack as="span">Họ và tên: <Stack as="strong" className="text-white">{receipt?.userName}</Stack></Stack>
                                        <Stack as="span">Số điện thoại: <Stack as="strong" className="text-white">{receipt?.userPhone}</Stack></Stack>
                                        <Stack as="span">Mã tài khoản khách: <Stack as="strong" className="text-white font-monospace">OU-USER-{receipt?.userPaidId}</Stack></Stack>
                                    </Stack>
                                </Col>
                                <Col md={6} className="ps-md-4">
                                    <Stack gap={2}>
                                        <Card.Title as="h6" className="text-gold fw-bold text-uppercase mb-1 tracking-wider">Chi tiết lưu trú</Card.Title>
                                        <Stack as="span">Thời điểm Check-in: <Stack as="strong" className="text-white">{receipt?.timeCheckIn}</Stack></Stack>
                                        <Stack as="span">Thời điểm Check-out: <Stack as="strong" className="text-white">{receipt?.timeCheckOut}</Stack></Stack>
                                        <Stack as="span">Nhân viên lập hóa đơn: <Stack as="strong" className="text-white">{receipt?.staffName} (ID: {receipt?.staffId})</Stack></Stack>
                                    </Stack>
                                </Col>
                            </Row>

                            <Card.Title as="h5" className="text-white fw-bold mb-3">
                                <Stack as="i" className="bi bi-list-task text-gold me-2"></Stack>Danh mục dịch vụ sử dụng
                            </Card.Title>

                            <Stack gap={2} className="mb-5">
                                <Stack direction="horizontal" className="justify-content-between p-3 receipt-item-row align-items-center flex-wrap gap-2">
                                    <Stack>
                                        <Stack as="span" className="text-white fw-bold">Thuê phòng khách sạn: Phòng {receipt?.roomName}</Stack>
                                        <Stack as="span" className="text-white-50 small">Hạng phòng: {receipt?.roomTypeName} | Thời lượng: {receipt?.totalNights} đêm</Stack>
                                    </Stack>
                                    <Stack className="text-end">
                                        <Stack as="span" className="text-white fw-bold">{formatVND(receipt?.roomAmount)}</Stack>
                                        <Stack as="span" className="text-white-50 small">({formatVND(receipt?.pricePerNight)} / đêm)</Stack>
                                    </Stack>
                                </Stack>

                                {receipt?.services?.map((service) => (
                                    <Stack key={service.roomBookingServiceId} direction="horizontal" className="justify-content-between p-3 receipt-item-row align-items-center flex-wrap gap-2">
                                        <Stack>
                                            <Stack as="span" className="text-white fw-bold">Dịch vụ tiện ích: {service.serviceName}</Stack>
                                            <Stack as="span" className="text-white-50 small">Số lượng đăng ký: {service.quantity}</Stack>
                                        </Stack>
                                        <Stack className="text-end">
                                            <Stack as="span" className="text-white fw-bold">{formatVND(service.unitPriceAtUse * service.quantity)}</Stack>
                                            <Stack as="span" className="text-white-50 small">({formatVND(service.unitPriceAtUse)} / dịch vụ)</Stack>
                                        </Stack>
                                    </Stack>
                                ))}
                            </Stack>

                            <Row className="justify-content-end">
                                <Col md={6}>
                                    <Stack gap={3} className="p-3 receipt-total-panel">
                                        <Stack direction="horizontal" className="justify-content-between text-white-50 small">
                                            <Stack as="span">Tiền thuê phòng vật lý:</Stack>
                                            <Stack as="span" className="text-white">{formatVND(receipt?.roomAmount)}</Stack>
                                        </Stack>
                                        <Stack direction="horizontal" className="justify-content-between text-white-50 small">
                                            <Stack as="span">Tổng chi phí dịch vụ tiện ích:</Stack>
                                            <Stack as="span" className="text-white">
                                                {formatVND(receipt?.services?.reduce((sum, s) => sum + (s.quantity * s.unitPriceAtUse), 0) || 0)}
                                            </Stack>
                                        </Stack>
                                        <Stack direction="horizontal" className="justify-content-between text-white-50 small">
                                            <Stack as="span">Khấu trừ đặc quyền:</Stack>
                                            <Stack as="span" className="text-danger">-{formatVND(receipt?.vipDiscountAmount)}</Stack>
                                        </Stack>
                                        <Stack className="divider-1px border-top border-secondary opacity-30 my-1 w-100"></Stack>
                                        <Stack direction="horizontal" className="justify-content-between align-items-end">
                                            <Stack as="span" className="text-white fw-bold mb-1">Tổng thực thu hệ thống:</Stack>
                                            <Stack as="span" className="text-gold fw-bold fs-3">{formatVND(receipt?.totalPrice)}</Stack>
                                        </Stack>
                                    </Stack>
                                </Col>
                            </Row>

                            <Stack direction="horizontal" gap={3} className="justify-content-center receipt-actions-wrapper print-hide">
                                <Button
                                    variant="outline-warning"
                                    className="px-4 rounded-pill fw-bold"
                                    onClick={() => window.print()}
                                >
                                    <Stack as="i" className="bi bi-download me-2"></Stack>Lưu hóa đơn (In PDF)
                                </Button>
                                <Button
                                    variant="warning"
                                    className="px-4 rounded-pill fw-bold text-dark btn-luxury-glow"
                                    onClick={() => navigate("/my-bookings")}
                                >
                                    <Stack as="i" className="bi bi-check-circle me-2"></Stack>Hoàn tất & Về lịch sử đặt
                                </Button>
                            </Stack>

                            <Stack className="text-center mt-5 pt-4 border-top border-secondary opacity-50 align-items-center">
                                <Card.Title as="h6" className="text-gold receipt-footer-stamp">Thank you for staying with OU Hotel</Card.Title>
                                <Stack as="span" className="text-white-50 small">Hóa đơn điện tử có giá trị pháp lý lưu hành nội bộ hệ thống</Stack>
                            </Stack>

                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default ReceiptDetail;