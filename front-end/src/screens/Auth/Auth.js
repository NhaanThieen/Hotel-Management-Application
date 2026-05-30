import { useState } from "react";
import { Container, Row, Col, Card, Form, Button, Stack, Tabs, Tab } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import BackButton from "../../components/BackButton";
import toast from "react-hot-toast";

const Auth = () => {
    const navigate = useNavigate();
    const [activeTab, setActiveTab] = useState("login");
    const [loading, setLoading] = useState(false);

    const [loginData, setLoginData] = useState({ userName: "", password: "" });
    const [registerData, setRegisterData] = useState({ userName: "", password: "", confirmPassword: "", name: "", phone: "" });

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const res = await fetch("/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(loginData)
            });
            const data = await res.json();
            if (!res.ok) throw new Error(data.message);

            localStorage.setItem("token", data.token);
            localStorage.setItem("user", JSON.stringify(data.user));
            
            toast.success("Đăng nhập thành công!");
            navigate("/"); 
        } catch (error) {
            toast.error(error.message || "Có lỗi xảy ra khi đăng nhập");
        } finally {
            setLoading(false);
        }
    };

    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        if (registerData.password !== registerData.confirmPassword) {
            toast.error("Mật khẩu xác nhận không trùng khớp!");
            return;
        }
        setLoading(true);
        try {
            const res = await fetch("/api/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    userName: registerData.userName,
                    password: registerData.password,
                    name: registerData.name,
                    phone: registerData.phone
                })
            });
            const data = await res.json();
            if (!res.ok) throw new Error(data.message);

            toast.success("Đăng ký thành công! Vui lòng đăng nhập lại.");
            setActiveTab("login"); 
            setRegisterData({ userName: "", password: "", confirmPassword: "", name: "", phone: "" });
        } catch (error) {
            toast.error(error.message || "Có lỗi xảy ra khi đăng ký");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container fluid className="my-5 py-4 px-lg-5 d-flex flex-column justify-content-center" style={{ minHeight: "80vh" }}>
            <Stack className="mb-2 align-self-start">
                <BackButton label="Trang chủ" onClick={() => navigate("/")} />
            </Stack>

            <Row className="justify-content-center w-100 m-0">
                <Col xl={5} lg={6} md={8} sm={11}>
                    <Card className="glass-card-static-light border-0 shadow-lg overflow-hidden p-4">
                        <Card.Body>
                            <Card.Title as="h2" className="text-center text-gold fw-bold mb-4 luxury-title" style={{ fontFamily: "serif" }}>
                                {activeTab === "login" ? "Đăng nhập" : "Đăng ký"}
                            </Card.Title>

                            <Tabs
                                activeKey={activeTab}
                                onSelect={(k) => setActiveTab(k)}
                                className="custom-gold-tabs justify-content-center mb-4 border-0"
                            >
                                <Tab eventKey="login" title="Đăng nhập" />
                                <Tab eventKey="register" title="Đăng ký" />
                            </Tabs>

                            {activeTab === "login" ? (
                                <Form onSubmit={handleLoginSubmit}>
                                    <Form.Group className="mb-3">
                                        <Form.Label className="text-white-50 small mb-1">Tên người dùng</Form.Label>
                                        <Form.Control
                                            type="text" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Nhập tên người dùng..."
                                            value={loginData.userName} onChange={(e) => setLoginData({ ...loginData, userName: e.target.value })}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-4">
                                        <Form.Label className="text-white-50 small mb-1">Mật khẩu</Form.Label>
                                        <Form.Control
                                            type="password" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Nhập mật khẩu..."
                                            value={loginData.password} onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
                                        />
                                    </Form.Group>

                                    <Button variant="warning" type="submit" disabled={loading} className="w-100 fw-bold py-2 rounded-pill text-dark shadow btn-luxury-glow">
                                        {loading ? "ĐANG XỬ LÝ..." : "ĐĂNG NHẬP"}
                                    </Button>
                                </Form>
                            ) : (
                                <Form onSubmit={handleRegisterSubmit}>
                                    <Form.Group className="mb-3">
                                        <Form.Label className="text-white-50 small mb-1">Họ và tên</Form.Label>
                                        <Form.Control
                                            type="text" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Nhập họ tên đầy đủ..."
                                            value={registerData.name} onChange={(e) => setRegisterData({ ...registerData, name: e.target.value })}
                                        />
                                    </Form.Group>
                                    <Form.Group className="mb-3">
                                        <Form.Label className="text-white-50 small mb-1">Tên người dùng</Form.Label>
                                        <Form.Control
                                            type="text" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Nhập tên người dùng..."
                                            value={registerData.userName} onChange={(e) => setRegisterData({ ...registerData, userName: e.target.value })}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label className="text-white-50 small mb-1">Số điện thoại</Form.Label>
                                        <Form.Control
                                            type="tel" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Nhập số điện thoại liên hệ..."
                                            value={registerData.phone} onChange={(e) => setRegisterData({ ...registerData, phone: e.target.value })}
                                        />
                                    </Form.Group>


                                    <Form.Group className="mb-3">
                                        <Form.Label className="text-white-50 small mb-1">Mật khẩu</Form.Label>
                                        <Form.Control
                                            type="password" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Vui lòng nhập mật khẩu"
                                            value={registerData.password} onChange={(e) => setRegisterData({ ...registerData, password: e.target.value })}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-4">
                                        <Form.Label className="text-white-50 small mb-1">Xác nhận mật khẩu</Form.Label>
                                        <Form.Control
                                            type="password" required className="custom-dark-input bg-transparent text-white border-secondary py-2"
                                            placeholder="Nhập lại mật khẩu để xác nhận"
                                            value={registerData.confirmPassword} onChange={(e) => setRegisterData({ ...registerData, confirmPassword: e.target.value })}
                                        />
                                    </Form.Group>

                                    <Button variant="warning" type="submit" disabled={loading} className="w-100 fw-bold py-2 rounded-pill text-dark shadow btn-luxury-glow">
                                        {loading ? "ĐANG KHỞI TẠO..." : "ĐĂNG KÝ NGAY"}
                                    </Button>
                                </Form>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default Auth;