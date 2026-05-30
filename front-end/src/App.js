import React, { Suspense, lazy } from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";

import MySpinner from './components/MySpinner';
import Header from "./components/Header";
import Footer from "./components/Footer";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './styles/layout.css';
import './styles/component.css';
import ErrorBoundary from './components/ErrorBoundary';
import { Toaster } from 'react-hot-toast';

const Home = lazy(() => import("./screens/Home/Home"));
const RoomList = lazy(() => import("./screens/RoomList/RoomList"));
const RoomDetail = lazy(() => import("./screens/RoomDetail/RoomDetail"));
const Auth = lazy(() => import("./screens/Auth/Auth"));
const Profile = lazy(() => import("./screens/Profile/Profile"));
const MyBookings = lazy(() => import("./screens/MyBookings/MyBookings"));
const Checkout = lazy(() => import("./screens/Checkout/Checkout"));
const ReceiptDetail = lazy(() => import("./screens/ReceiptDetail/ReceiptDetail"));
const Services = lazy(() => import("./screens/Services/Services"));



const App = () => {
  return (
    <BrowserRouter>
      <ErrorBoundary>
        <Toaster position="top-right" reverseOrder={false} />
        <Suspense fallback={<MySpinner />}>
          <div className="bg-overlay">
            <Header />

            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/rooms" element={<RoomList />} />
              <Route path="/rooms/:id" element={<RoomDetail />} />
              <Route path="/login" element={<Auth />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/my-bookings" element={<MyBookings />} />
              <Route path="/checkout" element={<Checkout />} />
              <Route path="/receipt/:id" element={<ReceiptDetail />} />
              <Route path="/services" element={<Services />} />
            </Routes>

            <Footer />
          </div>
        </Suspense>
      </ErrorBoundary>
    </BrowserRouter>
  );
};

export default App;