import { useEffect, useState } from "react";
import { Container, Card, Stack, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import MySpinner from "../../components/MySpinner";
import BackButton from "../../components/BackButton";
import BookingCard from "../../components/BookingCard";
import ReviewModal from "../../components/ReviewModal";
import toast from "react-hot-toast";

const formatVND = (price) => new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
const formatDate = (dateStr) => {
    if (!dateStr) return "";
    const [year, month, day] = dateStr.split("-");
    return `${day}/${month}/${year}`;
};



const MyBookings = () => {
    const navigate = useNavigate();
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showReviewModal, setShowReviewModal] = useState(false);
    const [selectedBookingForReview, setSelectedBookingForReview] = useState(null);

    const handleOpenReview = (booking) => {
        setSelectedBookingForReview(booking);
        setShowReviewModal(true);
    };

    useEffect(() => {
        const userStr = localStorage.getItem("user");
        if (!userStr) {
            toast.error("Vui lòng đăng nhập!");
            navigate("/login");
            return;
        }
        const user = JSON.parse(userStr);

        const fetchAllHistory = async () => {
            try {
                const res = await fetch(`/api/history?userName=${user.userName}`);
                if (!res.ok) throw new Error("Không thể tải lịch sử.");
                const responseData = await res.json();
                setTransactions(responseData.data || []);
            } catch (error) {
                console.error("Lỗi:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchAllHistory();
    }, [navigate]);

    if (loading) return <MySpinner />;

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <BackButton className="mb-4" label="Quay lại" />

            <Stack className="mb-4">
                <Card.Title as="h2" className="text-gold fw-bold luxury-title">Lịch Sử Giao Dịch</Card.Title>
            </Stack>

            {transactions.length === 0 ? (
                <Card className="glass-card-static border-0 shadow-lg p-5 text-center">
                    <Card.Body>
                        <Stack as="i" className="bi bi-credit-card-2-back text-white-50 fs-1 mb-3"></Stack>
                        <Card.Title as="h4" className="text-white fw-bold mb-2">Bạn chưa có lịch sử giao dịch nào</Card.Title>
                        <Button variant="warning" className="fw-bold px-4 rounded-pill btn-luxury-glow" onClick={() => navigate("/rooms")}>
                            Đặt phòng ngay
                        </Button>
                    </Card.Body>
                </Card>
            ) : (
                <Stack gap={4}>
                    {transactions.map((item, idx) => (
                        <BookingCard
                            key={`${item.type}-${item.id}-${idx}`}
                            item={item}
                            formatVND={formatVND}
                            formatDate={formatDate}
                            onNavigate={(id) => navigate(`/receipt/${id}`)}
                            onReview={() => handleOpenReview(item)}
                        />
                    ))}
                </Stack>
            )}

            <ReviewModal 
                show={showReviewModal} 
                onHide={() => setShowReviewModal(false)}
                bookingInfo={selectedBookingForReview}
            />
        </Container>
    );
};

export default MyBookings;