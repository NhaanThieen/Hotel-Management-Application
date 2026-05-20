import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link, useLocation, useNavigate } from "react-router-dom";
import RoomSearchBar from "./RoomSearchBar.js"; 
import '../styles/component.css';
import '../styles/layout.css';

const Header = () => {
    const location = useLocation();
    const navigate = useNavigate();
    
    const pathsToShowAuth = ['/booking', '/services', '/admin'];
    const shouldShowAuth = pathsToShowAuth.some(path => location.pathname.startsWith(path));

    const handleSearch = (searchData) => {
        console.log("Dữ liệu tìm kiếm từ Header bắn ra:", searchData);
        
        navigate("/rooms", { state: searchData });
    };

    return (
        <Navbar className="glass-panel" data-bs-theme="dark" expand="xl">
            <Container fluid className="px-lg-5">
                <Navbar.Brand as={Link} to="/" className="text-gold luxury-title fs-1 fw-bold">OU Hotel</Navbar.Brand>
                
                <Navbar.Toggle aria-controls="luxury-navbar-nav" />
                <Navbar.Collapse id="luxury-navbar-nav">
                    <Nav className="me-auto ms-4 align-items-center w-100">
                        <Nav.Link as={Link} to="/" className="line-text-hover text-nowrap">Trang chủ</Nav.Link>
                        <Nav.Link as={Link} to="/rooms" className="line-text-hover text-nowrap ms-1">Đặt phòng trực tuyến</Nav.Link>
                        <Nav.Link as={Link} to="/services" className="line-text-hover text-nowrap ms-1">Dịch vụ khác</Nav.Link>
                        <Nav.Link as={Link} to="/customer" className="line-text-hover text-nowrap ms-1">Thông tin khách hàng</Nav.Link>
                        {!location.pathname.startsWith('/rooms') && <RoomSearchBar onSearch={handleSearch} />}
                    </Nav>

                    {shouldShowAuth && (
                        <Nav className="ms-auto">
                            <NavDropdown 
                                title={<><Navbar.Text as="i" className="bi bi-person-circle me-1"></Navbar.Text>Tài khoản</>} 
                                id="account-nav-dropdown" 
                                align="end"
                            >
                                <NavDropdown.Item as={Link} to="/profile">Hồ sơ cá nhân</NavDropdown.Item>
                                <NavDropdown.Item as={Link} to="/my-bookings">Lịch sử đặt phòng</NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item href="#logout" className="text-danger">Đăng xuất</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;