import { Button, Stack } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const BackButton = ({ label = "Quay lại", onClick, className = "" }) => {
    const navigate = useNavigate();

    const handleOnClick = () => {
        if (onClick) {
            onClick();
        } else {
            navigate(-1);
        }
    };
    return (
        <Button
            className={`btn-custom-back d-flex align-items-center px-3 py-2 rounded shadow-sm ${className}`}
            onClick={handleOnClick}
        >
            <i className="bi bi-chevron-left me-2" style={{ fontSize: '1rem', fontWeight: 'bold' }}></i>

            <Stack as="span" className="fw-semibold" style={{ fontSize: '0.95rem' }}>
                {label}
            </Stack>
        </Button>
    );
};

export default BackButton;