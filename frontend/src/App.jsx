import React from 'react';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import About from './components/About';
import Home from './components/Home/Home';

function App() {
  return (
    <>
      <Navbar />  {/* Navbar will persist across routes */}
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          {/* You can add other routes here */}
        </Routes>
      </div>
    </>
  );
}

export default App;
