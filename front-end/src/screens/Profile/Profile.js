import { useEffect, useState } from "react";
import { Container, Row, Col, Card, Form, Button, Stack, InputGroup, Image, Badge } from "react-bootstrap";
import MySpinner from "../../components/MySpinner";
import BackButton from "../../components/BackButton";
import toast from "react-hot-toast";

const Profile = () => {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);

    const [formData, setFormData] = useState({ name: "", phone: "", newPassword: "", confirmPassword: "" });
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const fetchProfile = async () => {
    try {
        const user = JSON.parse(localStorage.getItem('user'));
        
        if (!user || !user.userName) {
            throw new Error("Người dùng chưa đăng nhập");
        }

        const res = await fetch(`/api/customer/profile?userName=${user.userName}`);
        
        if (!res.ok) throw new Error("Không thể đồng bộ dữ liệu tài khoản.");
        
        const data = await res.json();
        
        setProfile(data);
        setFormData({ 
            name: data.name || "", 
            phone: data.phone || "", 
            newPassword: "", 
            confirmPassword: "" 
        });
    } catch (error) {
        console.error("Lỗi tải thông tin tài khoản:", error);
    } finally {
        setLoading(false);
    }
};
    useEffect(() => {
        fetchProfile();
    }, []);

    const handleUpdateProfile = async (e) => {
    e.preventDefault();
    if (formData.newPassword && formData.newPassword !== formData.confirmPassword) {
        toast.error("Mật khẩu xác nhận không trùng khớp!");
        return;
    }
    setLoading(true);
    try {
        const payload = { 
            userName: profile.username, 
            name: formData.name, 
            phone: formData.phone 
        };
        
        if (formData.newPassword) payload.password = formData.newPassword;

        const res = await fetch("/api/customer/profile", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        
        if (!res.ok) {
            const errData = await res.json();
            throw new Error(errData.message || "Lỗi cập nhật");
        }
        
        toast.success("Cập nhật thành công!");
        setIsEditing(false);
        fetchProfile(); 
    } catch (error) {
        toast.error(error.message);
        setLoading(false);
    }
};

    if (loading) return <MySpinner />;

    return (
        <Container fluid className="my-5 py-4 px-lg-5">
            <BackButton className="mb-4" label="Quay lại" />

            <Row className="g-5">
                <Col lg={4} className="text-center">
                    <Card className="glass-card-static border-0 shadow-lg p-4 mb-4 align-items-center">
                        <Stack className="position-relative mb-3 align-items-center">
                            <Image
                                src={profile?.avataURL}
                                alt="Avatar"
                                roundedCircle
                                className="border border-warning p-1 shadow-lg profile-avatar"
                            />
                        </Stack>
                        <Card.Title as="h3" className="text-white fw-bold mb-1">{profile?.name}</Card.Title>
                        <Stack as="span" className="text-white-50 small mb-2">@{profile?.username}</Stack>
                    </Card>

                    <Card className="membership-card border-0 p-4 shadow-lg text-start overflow-hidden position-relative">
                        <Stack className="membership-card-inner justify-content-between position-relative">
                            <Stack direction="horizontal" className="justify-content-between align-items-center">
                                <Stack as="span" className="membership-brand text-gold fw-bold">OU Hotel</Stack>
                            </Stack>

                            <Stack className="my-2">
                                <Stack as="span" className="membership-label-small text-white-50 text-uppercase tracking-wider">Hạng thành viên</Stack>
                                <Stack as="span" className="text-white fw-bold fs-3 text-uppercase">{profile?.tierName}</Stack>
                            </Stack>

                            <Stack direction="horizontal" className="justify-content-between align-items-end">
                                <Badge bg="warning" text="dark" className="px-3 py-2 rounded-pill fw-bold border border-light shadow">
                                    -{profile?.discountPercent}%
                                </Badge>
                            </Stack>
                        </Stack>
                    </Card>
                </Col>

                <Col lg={8}>
                    <Card className="glass-card-static border-0 shadow-lg p-4 h-100">
                        <Card.Body>
                            <Stack direction="horizontal" className="justify-content-between align-items-center mb-4 pb-3 border-bottom border-secondary">
                                <Card.Title as="h3" className="text-gold fw-bold mb-0 luxury-title">Hồ Sơ Tài Khoản</Card.Title>
                                {!isEditing && (
                                    <Button variant="outline-warning" className="fw-bold px-4 rounded-pill" onClick={() => setIsEditing(true)}>
                                        <Stack as="i" className="bi bi-pencil-square me-2"></Stack>Thay đổi thông tin
                                    </Button>
                                )}
                            </Stack>

                            <Form onSubmit={handleUpdateProfile}>
                                <Row className="g-4 mb-4">
                                    <Col md={6}>
                                        <Form.Group>
                                            <Form.Label className="text-white-50 small mb-1">Họ và tên thành viên</Form.Label>
                                            <Form.Control 
                                                type="text" required disabled={!isEditing}
                                                className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                                value={formData.name} onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                                            />
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group>
                                            <Form.Label className="text-white-50 small mb-1">Số điện thoại</Form.Label>
                                            <Form.Control 
                                                type="tel" required disabled={!isEditing}
                                                className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                                value={formData.phone} onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                                            />
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group>
                                            <Form.Label className="text-white-50 small mb-1">Hạng thành viên hiện tại</Form.Label>
                                            <Form.Control 
                                                type="text" readOnly
                                                className="bg-transparent text-white border-secondary py-2 cursor-not-allowed "
                                                value={`${profile?.tierName}`}
                                            />
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group>
                                            <Form.Label className="text-white-50 small mb-1">Tên đăng nhập (Cố định)</Form.Label>
                                            <Form.Control 
                                                type="text" readOnly
                                                className="bg-transparent text-white border-secondary py-2 cursor-not-allowed"
                                                value={profile?.username || ""}
                                            />
                                        </Form.Group>
                                    </Col>
                                </Row>

                                {isEditing && (
                                    <Stack className="w-100">
                                        <Stack className="divider-1px border-top border-secondary opacity-50 my-4 w-100"></Stack>
                                        <Card.Title as="h5" className="text-white fw-bold mb-3">Đổi mật khẩu bảo mật</Card.Title>
                                        <Row className="g-4">
                                            <Col md={6}>
                                                <Form.Group>
                                                    <Form.Label className="text-white-50 small mb-1">Mật khẩu mới</Form.Label>
                                                    <InputGroup>
                                                        <Form.Control 
                                                            type={showPassword ? "text" : "password"}
                                                            className="custom-dark-input bg-transparent text-white border-secondary py-2 input-no-border-right"
                                                            value={formData.newPassword} onChange={(e) => setFormData({ ...formData, newPassword: e.target.value })}
                                                        />
                                                        <InputGroup.Text className="bg-transparent border-secondary text-white-50 icon-eye-wrapper" onClick={() => setShowPassword(!showPassword)}>
                                                            <Stack as="i" className={showPassword ? "bi bi-eye-slash" : "bi bi-eye"}></Stack>
                                                        </InputGroup.Text>
                                                    </InputGroup>
                                                </Form.Group>
                                            </Col>
                                            <Col md={6}>
                                                <Form.Group>
                                                    <Form.Label className="text-white-50 small mb-1">Xác nhận mật khẩu</Form.Label>
                                                    <InputGroup>
                                                        <Form.Control 
                                                            type={showConfirmPassword ? "text" : "password"}
                                                            className="custom-dark-input bg-transparent text-white border-secondary py-2 input-no-border-right"
                                                            value={formData.confirmPassword} onChange={(e) => setFormData({ ...formData, confirmPassword: e.target.value })}
                                                        />
                                                        <InputGroup.Text className="bg-transparent border-secondary text-white-50 icon-eye-wrapper" onClick={() => setShowConfirmPassword(!showConfirmPassword)}>
                                                            <Stack as="i" className={showConfirmPassword ? "bi bi-eye-slash" : "bi bi-eye"}></Stack>
                                                        </InputGroup.Text>
                                                    </InputGroup>
                                                </Form.Group>
                                            </Col>
                                        </Row>
                                    </Stack>
                                )}

                                {isEditing && (
                                    <Stack direction="horizontal" gap={3} className="mt-5 justify-content-end">
                                        <Button variant="outline-light" className="px-4 rounded-pill fw-bold" onClick={() => setIsEditing(false)}>Hủy bỏ</Button>
                                        <Button variant="warning" type="submit" className="px-4 rounded-pill fw-bold text-dark btn-luxury-glow">Lưu thông tin</Button>
                                    </Stack>
                                )}
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Profile;