import React from 'react';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Hero from './components/Hero/Hero';
import About from './components/About';

function App() {
  return (
    <>
      <Navbar />  {/* Navbar will persist across routes */}
      <div className="App">
        <Routes>
          <Route path="/" element={<Hero />} />
          <Route path="/about" element={<About />} />
          {/* You can add other routes here */}
        </Routes>
      </div>
    </>
  );
}

export default App;
