import axios from "axios";

export const endpoints = {
    "login": "/api/auth/login",
    "rooms": "/api/rooms",
    "services": "/api/services",
    "bookings": "/api/bookings",
    "invoices": "/api/invoices",
};

export default axios.create({
    baseURL: "http://localhost:8080",
});