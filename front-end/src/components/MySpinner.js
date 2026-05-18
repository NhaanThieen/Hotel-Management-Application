import { Spinner, Stack } from "react-bootstrap";

const MySpinner = () => {
    return (
        <Stack className="justify-content-center align-items-center w-100 my-5 py-5">
        <Spinner animation="border" variant="warning" />
    </Stack>
    );
    
}

export default MySpinner;