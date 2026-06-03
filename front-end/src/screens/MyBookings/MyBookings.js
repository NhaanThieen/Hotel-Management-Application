import { useEffect, useState, useCallback } from "react";
import { Container, Card, Stack, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import MySpinner from "../../components/MySpinner";
import BackButton from "../../components/BackButton";
import BookingCard from "../../components/BookingCard";
import ReviewModal from "../../components/FeedbackModal";
import toast from "react-hot-toast";
import FeedbackModal from "../../components/FeedbackModal";

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

    const handleOpenReview = (booking) => {
        setSelectedBookingForReview(booking);
        setShowReviewModal(true);
    };

    const handleReviewSuccess = () => {
        if (selectedBookingForReview) {
            setTransactions(prevTransactions => 
                prevTransactions.map(item => 
                    item.id === selectedBookingForReview.id 
                        ? { ...item, isReviewed: true } 
                        : item
                )
            );
        }
    };

    const fetchAllHistory = useCallback(async () => {
        const username = getUsername();
        const token = localStorage.getItem("token");
        if (!username) return;

        try {
            setLoading(true);
            const res = await fetch(`/HotelManagementServer/api/secure/bookings/history?userName=${username}`, {
                headers: { "Authorization": `Bearer ${token}` }
            });
            if (!res.ok) throw new Error("Không thể tải lịch sử.");
            const responseData = await res.json();
            setTransactions(responseData.data || []);
        } catch (error) {
            console.error("Lỗi:", error);
            toast.error("Lỗi khi tải lịch sử giao dịch.");
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        if (!getUsername()) {
            toast.error("Vui lòng đăng nhập!");
            navigate("/login");
            return;
        }
        fetchAllHistory();
    }, [navigate, fetchAllHistory]);

    const handleCancelBooking = async (bookingId) => {
        if (!window.confirm("Bạn có chắc chắn muốn hủy đơn đặt phòng này không?")) return;
        
        const username = getUsername();
        const token = localStorage.getItem("token");

        try {
            const res = await fetch(`/HotelManagementServer/api/secure/bookings/${bookingId}/cancel?userName=${username}`, {
                method: "PUT",
                headers: { "Authorization": `Bearer ${token}` }
            });
            const data = await res.json();
            
            if (res.ok) {
                toast.success(data.message || "Hủy phòng thành công!");
                fetchAllHistory(); 
            } else {
                toast.error(data.message || "Hủy phòng thất bại.");
            }
        } catch (error) {
            toast.error("Lỗi kết nối đến máy chủ.");
        }
    };

    if (loading && transactions.length === 0) return <MySpinner />;

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <BackButton className="mb-4" label="Quay lại" />

            <Stack className="mb-4">
                <Card.Title as="h2" className="text-gold fw-bold luxury-title">Lịch Sử Giao Dịch</Card.Title>
            </Stack>

            {transactions.length === 0 && !loading ? (
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
                            onCancel={() => handleCancelBooking(item.id)} 
                        />
                    ))}
                </Stack>
            )}

            <FeedbackModal 
                show={showReviewModal} 
                onHide={() => setShowReviewModal(false)}
                bookingInfo={selectedBookingForReview}
                onSuccess={handleReviewSuccess} 
            />
        </Container>
    );
};

export default MyBookings;