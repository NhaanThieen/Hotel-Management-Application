import { useState } from "react";
import { Button, NavDropdown, Navbar, Stack } from "react-bootstrap";
import ReactSlider from 'react-slider';
import BookingDateRange from "./BookingDateRange";

const RoomSearchBar = ({ onSearch, initialValues = {}, hoverClass = "line-text-hover" }) => {
    const [checkInDate, setCheckInDate] = useState(initialValues.checkInDate || new Date());
    const [checkOutDate, setCheckOutDate] = useState(initialValues.checkOutDate || null);
    const [priceRange, setPriceRange] = useState(initialValues.priceRange || [500000, 5000000]);
    const [selectedRoomType, setSelectedRoomType] = useState(initialValues.roomType || "Theo phòng");
    const [isPriceChanged, setIsPriceChanged] = useState(false);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const formatShortPrice = (price) => {
        if (price >= 1000000) return (price / 1000000) + 'tr';
        if (price >= 1000) return (price / 1000) + 'k';
        return price + 'đ';
    };

    const handleSearchSubmit = () => {
        if (onSearch) {
            onSearch({
                checkInDate,
                checkOutDate,
                roomType: selectedRoomType,
                priceRange
            });
        }
    };

    return (
        <Stack direction="horizontal" className="align-items-center">

            <BookingDateRange
                checkInDate={checkInDate}
                setCheckInDate={setCheckInDate}
                checkOutDate={checkOutDate}
                setCheckOutDate={setCheckOutDate}
                containerClassName="align-items-center"
                itemClassName={`nav-calendar-wrapper px-2 ${hoverClass}`}
                labelClassName="calendar-nav-label navbar-text p-0 d-block"
                inputClassName="header-datepicker-input ms-1"
            />

            <NavDropdown title={selectedRoomType} id="room-search-dropdown" className="me-2 text-white-50" menuVariant="dark">
                <NavDropdown.Item onClick={() => setSelectedRoomType("Tất cả phòng")}>Tất cả phòng</NavDropdown.Item>
                <NavDropdown.Item onClick={() => setSelectedRoomType("Phòng VIP")}>Phòng VIP</NavDropdown.Item>
                <NavDropdown.Item onClick={() => setSelectedRoomType("Phòng thường")}>Phòng thường</NavDropdown.Item>
            </NavDropdown>

            <NavDropdown
                title={isPriceChanged ? `${formatShortPrice(priceRange[0])} - ${formatShortPrice(priceRange[1])}` : "Theo giá"}
                id="price-search-dropdown"
                autoClose="outside"
                className="ms-1 text-white-50"
                menuVariant="dark"
            >
                <NavDropdown.ItemText className="custom-price-dropdown px-3 py-2" style={{ width: '320px' }}>
                    <NavDropdown.Header className="text-gold mb-3 fw-bold px-0">Chọn mức giá</NavDropdown.Header>

                    <ReactSlider
                        className="gold-slider"
                        thumbClassName="gold-slider-thumb"
                        trackClassName="gold-slider-track"
                        min={0} max={10000000} step={100000}
                        value={priceRange}
                        onChange={(val) => { setPriceRange(val); setIsPriceChanged(true); }}
                        renderTrack={(props, state) => <Stack key={props.key} {...props} className={state.index === 1 ? "track-active" : "track-inactive"} />}
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

            <Button variant="outline-light" className="btn-luxury-glow ms-3" onClick={handleSearchSubmit}>
                Tìm kiếm
            </Button>

        </Stack>
    );
};

export default RoomSearchBar;