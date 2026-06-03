import React, { useState } from "react";
import { Modal, Button, Form, Stack, Row, Col } from "react-bootstrap";
import toast from "react-hot-toast";

const FeedbackModal = ({ show, onHide, bookingInfo, onSuccess }) => {
    const [ratings, setRatings] = useState({
        reception: 5,
        foodAndDrink: 5,
        cleanliness: 5,
        comfort: 5
    });

    const availableTags = [
        "Nhân viên thân thiện", "Làm thủ tục nhanh chóng", 
        "Phòng sạch sẽ", "View xịn xò", "Đồ ăn rất ngon", 
        "Giường êm ái", "Giá cả hợp lý", "Cần cải thiện cách âm"
    ];
    const [selectedTags, setSelectedTags] = useState([]);

    const [comment, setComment] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleRatingChange = (category, value) => {
        setRatings(prev => ({ ...prev, [category]: value }));
    };

    const toggleTag = (tag) => {
        setSelectedTags(prev => 
            prev.includes(tag) 
                ? prev.filter(t => t !== tag) 
                : [...prev, tag]
        );
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

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);

        const token = localStorage.getItem("token");

        const payload = {
            bookingId: bookingInfo?.id || null,
            userName: getUsername(), 
            ratings,
            tags: selectedTags,
            comment
        };

        try {
            const res = await fetch("/HotelManagementServer/api/secure/feedbacks", {
                method: "POST",
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}` 
                },
                body: JSON.stringify(payload)
            });
            
            if (!res.ok) throw new Error("Lỗi khi gửi đánh giá");

            toast.success("Cảm ơn bạn đã đánh giá! Trải nghiệm của bạn rất quý giá với chúng tôi.");
            setComment("");
            setSelectedTags([]);

            if (onSuccess) onSuccess();

            onHide(); 
        } catch (error) {
            toast.error(error.message || "Xảy ra sự cố, vui lòng thử lại sau.");
        } finally {
            setIsSubmitting(false);
        }
    };

    const renderStarRating = (label, category) => (
        <Stack direction="horizontal" className="justify-content-between align-items-center mb-3">
            <Stack as="span" className="text-white fw-bold">{label}</Stack>
            <Stack direction="horizontal" gap={1}>
                {[1, 2, 3, 4, 5].map((star) => (
                    <Stack 
                        as="i" 
                        key={star}
                        className={`bi ${star <= ratings[category] ? 'bi-star-fill text-gold' : 'bi-star text-white-50'} fs-4`}
                        style={{ cursor: "pointer" }}
                        onClick={() => handleRatingChange(category, star)}
                    ></Stack>
                ))}
            </Stack>
        </Stack>
    );

    return (
        <Modal 
            show={show} 
            onHide={onHide} 
            centered 
            size="lg"
            contentClassName="glass-modal-content" 
            backdrop="static" 
        >
            <Modal.Header closeButton closeVariant="white" className="glass-modal-header border-bottom border-secondary opacity-75">
                <Modal.Title className="text-gold luxury-title fw-bold">
                    <i className="bi bi-envelope-paper-heart me-2"></i>Đánh giá trải nghiệm
                </Modal.Title>
            </Modal.Header>

            <Modal.Body className="p-4 p-md-5">
                <Form onSubmit={handleSubmit}>
                    <Stack className="text-center mb-4">
                        <Stack as="span" className="text-white-50">
                            Cảm ơn quý khách đã tin tưởng và sử dụng dịch vụ tại <strong className="text-gold">OU Hotel</strong>.
                        </Stack>
                        <Stack as="span" className="text-white-50 small mt-1">
                            Vui lòng dành 1 chút thời gian để đánh giá chất lượng phục vụ, xin cảm ơn!
                        </Stack>
                    </Stack>

                    <Row className="g-4 mb-4">
                        <Col md={6}>
                            <div className="checkout-receipt-panel p-3 h-100">
                                <Stack as="h6" className="text-gold fw-bold mb-3 border-bottom border-secondary pb-2">
                                    Đánh giá chi tiết
                                </Stack>
                                {renderStarRating("Thái độ Lễ tân", "reception")}
                                {renderStarRating("Chất lượng Phòng", "cleanliness")}
                                {renderStarRating("Đồ ăn & Thức uống", "foodAndDrink")}
                                {renderStarRating("Tiện nghi chung", "comfort")}
                            </div>
                        </Col>

                        <Col md={6}>
                            <div className="checkout-receipt-panel p-3 h-100">
                                <Stack as="h6" className="text-gold fw-bold mb-3 border-bottom border-secondary pb-2">
                                    Điều gì làm bạn ấn tượng?
                                </Stack>
                                <div className="d-flex flex-wrap gap-2">
                                    {availableTags.map(tag => (
                                        <Button
                                            key={tag}
                                            variant="outline-light"
                                            size="sm"
                                            className={`rounded-pill ${selectedTags.includes(tag) ? 'btn-gold text-dark' : 'text-white-50 border-secondary'}`}
                                            onClick={() => toggleTag(tag)}
                                        >
                                            {tag}
                                        </Button>
                                    ))}
                                </div>
                            </div>
                        </Col>
                    </Row>

                    <Form.Group className="mb-4">
                        <Form.Label className="text-white fw-bold">Thêm ý kiến (Tùy chọn)</Form.Label>
                        <Form.Control 
                            as="textarea" 
                            rows={3} 
                            placeholder="Chia sẻ thêm cảm nhận của bạn"
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                            className="custom-dark-input bg-transparent text-white border-secondary"
                        />
                    </Form.Group>

                    <Stack direction="horizontal" gap={3} className="justify-content-end">
                        <Button variant="link" className="text-white-50 text-decoration-none" onClick={onHide}>
                            Để sau
                        </Button>
                        <Button 
                            type="submit" 
                            disabled={isSubmitting}
                            className="btn-luxury-glow fw-bold px-5"
                        >
                            {isSubmitting ? "Đang gửi..." : "Gửi Đánh Giá"}
                        </Button>
                    </Stack>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default FeedbackModal;