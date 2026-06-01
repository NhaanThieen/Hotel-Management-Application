import { useEffect, useState } from "react";
import { Button, Card, Col, Container, Pagination, Row, Stack } from "react-bootstrap";
import MySpinner from "../../components/MySpinner";
import RoomListDisplay from "../../components/RoomListDisplay";

const Home = () => {

    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const limitPerPage = 6;

    useEffect(() => {
        setLoading(true);
        fetch(`/api/rooms?page=${currentPage}&limit=${limitPerPage}`)
            .then(res => res.json())
            .then(response => {
                const roomData = response.data || response || [];
                setRooms(roomData);
                setTotalPages(response.totalPages || 1);
                setTimeout(() => {
                    setLoading(false);
                }, 1000);
            })
            .catch(error => console.error("Lỗi khi kéo dữ liệu phòng:", error));
    }, [currentPage]);

    const formatVND = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
        const scrollContainer = document.querySelector('.bg-overlay');
        if (scrollContainer) {
            scrollContainer.scrollTo({ top: 0, behavior: 'smooth' });
        }
    };

    return (
        <Container className="my-5 py-5">
            <Stack className="text-center mb-5">
                <Stack as="h2" className="text-gold luxury-title display-5 fw-bold mb-3">
                    Phòng Nổi Bật
                </Stack>
                <Stack as="p" className="text-white-50">
                    Khám phá không gian nghỉ dưỡng tinh tế
                </Stack>
            </Stack>

            <RoomListDisplay 
                variant="grid" 
                rooms={rooms} 
                loading={loading} 
            />
        </Container>
    );
};
export default Home;

        // <Container className="my-5 py-5">
        //     <Stack className="text-center mb-5">
        //         <Stack as="h2" className="text-gold luxury-title display-5 fw-bold mb-3">
        //             Phòng Nổi Bật
        //         </Stack>
        //     </Stack>
        //     {loading ? (
        //         <MySpinner />
        //     ) : (
        //         <>
        //             <Row className="g-4 justify-content-center">
        //                 {rooms?.map((room) => (
        //                     <Col lg={4} md={6} sm={12} key={room.id}>
        //                         <Card className="glass-card h-100 border-0 shadow-lg">
        //                             <Stack className="position-relative overflow-hidden">
        //                                 <Card.Img variant="top" src={room.image} className="room-img" />
        //                             </Stack>
        //                             <Card.Body className="d-flex flex-column p-4">
        //                                 <Card.Title className="text-white fw-bold fs-4 mb-3">{room.name}</Card.Title>

        //                                 <Stack direction="horizontal" className="mt-auto justify-content-between align-items-end">
        //                                     <Stack>
        //                                         <Card.Text as="small" className="text-white-50 d-block mb-1">
        //                                             Giá mỗi đêm từ
        //                                         </Card.Text>
        //                                         <Card.Text as="span" className="price-tag fs-5">
        //                                             {formatVND(room.price)}
        //                                         </Card.Text>
        //                                     </Stack>
        //                                     <Button variant="outline-light" className="btn-luxury-glow px-4 py-2 fw-bold">
        //                                         Đặt ngay
        //                                     </Button>
        //                                 </Stack>

        //                             </Card.Body>
        //                         </Card>
        //                     </Col>
        //                 ))}
        //             </Row>


        //             {totalPages > 1 && (
        //                 <Stack direction="horizontal" className="justify-content-center mt-5">
        //                     <Pagination className="custom-gold-pagination">
        //                         <Pagination.Prev
        //                             onClick={() => handlePageChange(currentPage - 1)}
        //                             disabled={currentPage === 1}
        //                         />

        //                         {[...Array(totalPages)].map((_, index) => (
        //                             <Pagination.Item
        //                                 key={index + 1}
        //                                 active={index + 1 === currentPage}
        //                                 onClick={() => handlePageChange(index + 1)}
        //                             >
        //                                 {index + 1}
        //                             </Pagination.Item>
        //                         ))}

        //                         <Pagination.Next
        //                             onClick={() => handlePageChange(currentPage + 1)}
        //                             disabled={currentPage === totalPages}
        //                         />
        //                     </Pagination>
        //                 </Stack>
        //             )}
        //         </>
        //     )}
        // </Container>

    // );
// }

