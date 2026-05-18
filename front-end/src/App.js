import { BrowserRouter, Routes, Route } from "react-router-dom"
import Header from "./components/Header";
import Footer from "./components/Footer";
import Home from "./Home/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles/layout.css';
import './styles/component.css';

const App = () => {
  return (
    <BrowserRouter>
      <div className="bg-overlay">
        <Header />

        <Routes >
          <Route path="/" element={<Home />} />
        </Routes>


        <Footer />
      </div >
    </BrowserRouter>


  );
};

export default App;