import { Button, Container, Nav, Navbar, NavDropdown, Stack } from "react-bootstrap";
import '../styles/component.css';
import '../styles/layout.css';
import DatePicker from 'react-datepicker';
import { Link, useLocation } from "react-router-dom";
import 'react-datepicker/dist/react-datepicker.css';
import { vi } from 'date-fns/locale/vi';
import { useState } from "react";
import ReactSlider from 'react-slider';

const Header = () => {
    const location = useLocation();
    const pathsToShowAuth = ['/booking', '/services', '/admin'];
    const shouldShowAuth = pathsToShowAuth.some(path => location.pathname.startsWith(path));

    const [checkInDate, setCheckInDate] = useState(new Date());
    const [checkOutDate, setCheckOutDate] = useState(null);
    const [priceRange, setPriceRange] = useState([500000, 5000000]);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    return (
        <Navbar className="glass-panel" data-bs-theme="dark" expand="xl">
            <Container fluid className="px-lg-5">
                <Navbar.Brand as={Link} to="/" className="text-gold luxury-title fs-1">Khách sạn OU</Navbar.Brand>
                
                <Nav className="me-auto ms-4 align-items-center">
                    <Nav.Link as={Link} to="/" className="line-text-hover">Trang chủ</Nav.Link>
                    <Nav.Link as={Link} to="/bookings" className="line-text-hover">Đặt phòng trực tuyến</Nav.Link>
                    <Nav.Link as={Link} to="/services" className="line-text-hover">Dịch vụ khác</Nav.Link>
                    <Nav.Link as={Link} to="/customer" className="line-text-hover">Thông tin khách hàng</Nav.Link>
                    
                    <Nav.Item className="nav-calendar-wrapper line-text-hover px-2">
                        <Navbar.Text className="calendar-nav-label">Nhận phòng</Navbar.Text>
                        <DatePicker
                            locale={vi}
                            selected={checkInDate}
                            onChange={(date) => setCheckInDate(date)}
                            selectsStart
                            startDate={checkInDate}
                            endDate={checkOutDate}
                            minDate={new Date()}
                            dateFormat="dd/MM/yyyy"
                            className="header-datepicker-input"
                            calendarClassName="glass-datepicker"
                            popperPlacement="bottom-start"
                        />
                    </Nav.Item>
                    
                    <Nav.Item className="nav-calendar-wrapper line-text-hover px-2 ms-lg-2">
                        <Navbar.Text className="calendar-nav-label">Trả phòng</Navbar.Text>
                        <DatePicker
                            locale={vi}
                            selected={checkOutDate}
                            onChange={(date) => setCheckOutDate(date)}
                            selectsEnd
                            startDate={checkInDate}
                            endDate={checkOutDate}
                            minDate={checkInDate}
                            dateFormat="dd/MM/yyyy"
                            placeholderText="Chọn ngày"
                            className="header-datepicker-input"
                            calendarClassName="glass-datepicker"
                            popperPlacement="bottom-start"
                        />
                    </Nav.Item>
                    
                    <NavDropdown title="Theo phòng" id="room-nav-dropdown">
                        <NavDropdown.Item href="#action/vip">Phòng VIP</NavDropdown.Item>
                        <NavDropdown.Item href="#action/standard">Phòng thường</NavDropdown.Item>
                    </NavDropdown>
                    
                    <NavDropdown title="Theo giá" id="price-nav-dropdown" autoClose="outside">
                        <NavDropdown.ItemText className="custom-price-dropdown px-3 py-2" style={{ width: '320px' }}>
                            <NavDropdown.Header className="text-gold mb-3 fw-bold px-0">Chọn mức giá</NavDropdown.Header>
                            
                            <ReactSlider
                                className="gold-slider"
                                thumbClassName="gold-slider-thumb"
                                trackClassName="gold-slider-track"
                                min={0}
                                max={10000000} 
                                step={100000}  
                                value={priceRange}
                                onChange={(val) => setPriceRange(val)}
                                renderTrack={(props, state) => {
                                    const { key, ...restProps } = props;
                                    return (
                                        <Stack 
                                            key={key} 
                                            {...restProps} 
                                            className={state.index === 1 ? "track-active" : "track-inactive"} 
                                        />
                                    );
                                }}
                            />
                            
                            <Stack direction="horizontal" className="justify-content-between mt-3">
                                <Stack className="price-box text-center">
                                    <Navbar.Text as="small" className="text-white-50 d-block">Từ</Navbar.Text>
                                    <Navbar.Text as="span" className="fw-bold text-white">{formatVND(priceRange[0])}</Navbar.Text>
                                </Stack>
                                <Stack className="price-box text-center">
                                    <Navbar.Text as="small" className="text-white-50 d-block">Đến</Navbar.Text>
                                    <Navbar.Text as="span" className="fw-bold text-white">{formatVND(priceRange[1])}</Navbar.Text>
                                </Stack>
                            </Stack>
                        </NavDropdown.ItemText>
                    </NavDropdown>
                    
                    <Button variant="outline-light" className="btn-luxury-glow ms-3">Tìm kiếm</Button>
                </Nav>

                {shouldShowAuth && (
                    <Nav>
                        <NavDropdown 
                            title={
                                <>
                                    <Navbar.Text as="i" className="bi bi-person-circle me-1"></Navbar.Text>
                                    Tài khoản
                                </>
                            } 
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
            </Container>
        </Navbar>
    );
}

export default Header;