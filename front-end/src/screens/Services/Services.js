import { useEffect, useState } from "react";
import { Container, Row, Col, Card, Stack, Badge, Button, Image, Modal } from "react-bootstrap";
import MySpinner from "../../components/MySpinner";
import BackButton from "../../components/BackButton";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

const Services = () => {
    const navigate = useNavigate();
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showModal, setShowModal] = useState(false);
    const [selectedService, setSelectedService] = useState(null);
    const [isBooking, setIsBooking] = useState(false);

    useEffect(() => {
        const fetchServices = async () => {
            try {
                const res = await fetch("/HotelManagementServer/api/services");
                if (!res.ok) throw new Error("Không thể tải danh sách dịch vụ.");
                
                const data = await res.json();
                const activeServices = data.data.filter(item => item.isDeleted === 0); 
                setServices(activeServices);
            } catch (error) {
                console.error("Lỗi kết nối dịch vụ:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchServices();
    }, []);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const handleOpenModal = (service) => {
        setSelectedService(service);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        if (!isBooking) {
            setShowModal(false);
            setSelectedService(null);
        }
    };

    const getUsername = () => {
        let userStr = localStorage.getItem("user");
        if (!userStr) return null;
        try {
            const userObj = JSON.parse(userStr);
            return userObj.username || userObj.userName || userObj;
        } catch (e) {
            return userStr.replace(/^["']|["']$/g, '');
        }
    };

    const confirmBookingService = async () => {
        setIsBooking(true);
        try {
            const userName = getUsername();
            const token = localStorage.getItem("token");

            if (!userName || !token) {
                toast.error("Vui lòng đăng nhập hệ thống để sử dụng dịch vụ!");
                navigate("/login");
                return;
            }

            const res = await fetch("/HotelManagementServer/api/secure/service-booking", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    userName: userName,
                    serviceId: selectedService.serviceId,
                    serviceName: selectedService.name,
                    price: selectedService.price,
                    stock: 1
                })
            });
            
            const data = await res.json();
            if (!res.ok) throw new Error(data.message);

            toast.success(data.message);
            handleCloseModal();

            navigate(`/receipt/${data.data.receiptId}`); 
        } catch (error) {
            toast.error(error.message || "Xảy ra sự cố khi đăng ký dịch vụ.");
        } finally {
            setIsBooking(false);
        }
    };

    if (loading) return <MySpinner />;

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <BackButton className="mb-4" label="Trang chủ" />

            <Stack className="mb-5 text-center align-items-center">
                <Card.Title as="h2" className="text-gold fw-bold luxury-title mb-3">Dịch Vụ & Tiện Ích Đẳng Cấp</Card.Title>
            </Stack>

            <Row className="g-4">
                {services.map((service) => (
                    <Col xl={4} md={6} key={service.serviceId}>
                        <Card className="glass-card-static service-card border-0 shadow-lg h-100">

                            <Stack className="service-img-wrapper">
                                <Badge bg="dark" className="service-badge-overlay border border-warning text-gold px-3 py-2 rounded-pill fw-bold">
                                    {service.type || "Nổi bật"}
                                </Badge>
                                <Image
                                    src={service.imgURL}
                                    alt={service.name}
                                    className="service-img"
                                />
                            </Stack>

                            <Card.Body as={Stack} className="service-content-box justify-content-between p-4">
                                <Stack>
                                    <Card.Title as="h4" className="text-white fw-bold mb-2">{service.name}</Card.Title>
                                </Stack>

                                <Stack direction="horizontal" className="justify-content-between align-items-end mt-4 border-top border-secondary pt-3 opacity-75">
                                    <Stack>
                                        <Stack as="span" className="text-white-50 small mb-1">Mức giá tham khảo:</Stack>
                                        <Stack as="span" className="text-gold fw-bold fs-5">{formatVND(service.price)}</Stack>
                                    </Stack>
                                    <Button
                                        variant="outline-warning"
                                        className="rounded-pill fw-bold px-4"
                                        onClick={() => {
                                            setSelectedService(service);
                                            setShowModal(true);
                                        }}
                                    >
                                        Đăng ký
                                    </Button>
                                </Stack>
                            </Card.Body>

                        </Card>
                    </Col>
                ))}
            </Row>

            <Modal
                show={showModal}
                onHide={handleCloseModal}
                centered
                contentClassName="glass-modal-content"
                backdrop="static"
            >
                <Modal.Header closeButton closeVariant="white" className="glass-modal-header">
                    <Modal.Title as="h5" className="text-gold fw-bold luxury-title mb-0">Xác Nhận Đăng Ký</Modal.Title>
                </Modal.Header>

                <Modal.Body className="text-center py-5">
                    <Stack as="i" className="bi bi-question-circle text-gold modal-service-icon mb-4"></Stack>
                    <Stack as="span" className="text-white fs-5 mb-2">Bạn có chắc chắn muốn đăng ký dịch vụ</Stack>
                    <Stack as="span" className="text-gold fw-bold fs-4 mb-4">{selectedService?.name}</Stack>

                    <Stack className="bg-dark p-3 rounded border border-secondary mx-4">
                        <Stack as="span" className="text-white-50 small mb-1">Chi phí sẽ được tự động cộng vào biên lai:</Stack>
                        <Stack as="span" className="text-white fw-bold fs-5">{selectedService ? formatVND(selectedService.price) : ""}</Stack>
                    </Stack>
                </Modal.Body>

                <Modal.Footer className="glass-modal-header justify-content-center border-0 pb-4">
                    <Button
                        variant="outline-light"
                        className="rounded-pill px-4 fw-bold me-2"
                        onClick={handleCloseModal}
                        disabled={isBooking}
                    >
                        Hủy bỏ
                    </Button>
                    <Button
                        variant="warning"
                        className="rounded-pill px-4 fw-bold text-dark btn-luxury-glow"
                        onClick={confirmBookingService}
                        disabled={isBooking}
                    >
                        {isBooking ? "ĐANG XỬ LÝ..." : "XÁC NHẬN ĐĂNG KÝ"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default Services;