import { Col, Container, Dropdown, Nav, Navbar, Row, Stack } from "react-bootstrap";
const Footer = () => {
    return (
        <Container fluid as="footer" className="glass-panel footer-glass mt-5" data-bs-theme="dark">
            <Container className="py-5">
                <Row className="gy-4 justify-content-center">
                    
                    <Col md={6} className="d-flex justify-content-md-end justify-content-center pe-md-5">
                        <Stack className="align-items-center text-center">
                            <Navbar.Text as="h3" className="text-gold luxury-title mb-3 fs-2 text-md-end text-center">
                                Khách sạn OU
                            </Navbar.Text>
                        </Stack>
                    </Col>
                        
                    <Col md={6} className="d-flex justify-content-center align-items-center">
                        <Stack className="align-items-center text-center">
                            <Navbar.Text as="h5" className="text-gold fw-bold text-uppercase mb-3 tracking-wider fs-6">
                                Liên hệ
                            </Navbar.Text>
                            
                            <Nav className="flex-column gap-3 text-white-50 list-unstyled">
                                <Nav.Item className="d-flex align-items-start gap-2">
                                    <Navbar.Text as="i" className="bi bi-geo-alt-fill text-gold mt-1"></Navbar.Text>
                                    <Navbar.Text as="span">97 Võ Văn Tần, Phường Võ Thị Sáu, Quận 3, TP. Hồ Chí Minh</Navbar.Text>
                                </Nav.Item>
                                
                                <Nav.Item className="d-flex align-items-center gap-2">
                                    <Navbar.Text as="i" className="bi bi-telephone-fill text-gold"></Navbar.Text>
                                    <Navbar.Text as="span">+84 (0) 28 3930 0077</Navbar.Text>
                                </Nav.Item>
                                
                                <Nav.Item className="d-flex align-items-center gap-2">
                                    <Navbar.Text as="i" className="bi bi-envelope-fill text-gold"></Navbar.Text>
                                    <Navbar.Text as="span">contact@ouhotel.com</Navbar.Text>
                                </Nav.Item>
                            </Nav>
                        </Stack>
                    </Col>

                </Row>

                <Dropdown.Divider className="border-secondary my-4" style={{ opacity: 0.2 }} />
                
                <Row className="align-items-center text-white-50 small">
                    <Col xs={12} className="text-center mb-2 mb-md-0">
                        <Navbar.Text>
                            &copy; 2026 Khách sạn OU. All rights reserved.
                        </Navbar.Text>
                    </Col>
                </Row>
            </Container>
        </Container>
    );
};

export default Footer;