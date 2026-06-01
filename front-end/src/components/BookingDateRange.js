import { set } from "date-fns";
import { se, vi } from "date-fns/locale";
import 'react-datepicker/dist/react-datepicker.css';
import { NavItem, Stack } from "react-bootstrap";
import DatePicker from "react-datepicker";

const BookingDateRange=({
    checkInDate,
    setCheckInDate,
    checkOutDate,
    setCheckOutDate,
    containerClassName,
    itemClassName="",
    labelClassName="",
    inputClassName=""

}) => {
    const handleCheckIn = (date) => {
        setCheckInDate(date);
        if (!checkOutDate || date > checkOutDate) {
            setCheckOutDate(date);
        }
    };

    const handleCheckOut = (date) => {
        setCheckOutDate(date);
        if (!checkInDate || date < checkInDate) {
            setCheckInDate(date);
        }
    };
    return (
        <Stack direction="horizontal" className={containerClassName}>
            <Stack className={itemClassName}>
                <Stack as="span" className={labelClassName}>Nhận phòng</Stack>
                <DatePicker
                    locale={vi}
                    selected={checkInDate}
                    onChange={handleCheckIn}
                    selectsStart
                    startDate={checkInDate}
                    endDate={checkOutDate}
                    minDate={new Date()} 
                    maxDate={checkOutDate} 
                    dateFormat="dd/MM/yyyy"
                    placeholderText="Chọn ngày"
                    className={inputClassName}
                    calendarClassName="glass-datepicker"
                    popperPlacement="bottom-start"
                />
            </Stack>
            
            <Stack className={itemClassName}>
                <Stack as="span" className={labelClassName}>Trả phòng</Stack>
                <DatePicker
                    locale={vi}
                    selected={checkOutDate}
                    onChange={handleCheckOut}
                    selectsEnd
                    startDate={checkInDate}
                    endDate={checkOutDate}
                    minDate={checkInDate || new Date()} 
                    dateFormat="dd/MM/yyyy"
                    placeholderText="Chọn ngày"
                    className={inputClassName}
                    calendarClassName="glass-datepicker"
                    popperPlacement="bottom-start"
                />
            </Stack>
        </Stack>
    );
};
export default BookingDateRange;

