import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom'; 
import './Navbar.css';

const Navbar = () => {
  const [exploreDropdown, setExploreDropdown] = useState(false);
  const [licenseDropdown, setLicenseDropdown] = useState(false);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const location = useLocation();  // Get the current location

  // Check if we are on the Hero page (home)
  const isHeroPage = location.pathname === "/";  // The Hero page path

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  return (
    <div className={`header ${isHeroPage ? '' : 'non-hero-page'}`}>
      <nav className="navbar">
        <div className="navbar-logo">Pexels</div>
        <div className="navbar-hamburger" onClick={toggleMobileMenu}>
          ☰
        </div>
        <ul className={`navbar-links ${isMobileMenuOpen ? 'open' : ''}`}>
          <li
            onMouseEnter={() => setExploreDropdown(true)}
            onMouseLeave={() => setExploreDropdown(false)}
          >
            <Link to="/explore" onClick={toggleMobileMenu}>Explore</Link>
            {exploreDropdown && (
              <ul className="dropdown">
                <li><Link to="/explore/photos" onClick={toggleMobileMenu}>Photos</Link></li>
                <li><Link to="/explore/videos" onClick={toggleMobileMenu}>Videos</Link></li>
                <li><Link to="/explore/collections" onClick={toggleMobileMenu}>Collections</Link></li>
              </ul>
            )}
          </li>
          <li
            onMouseEnter={() => setLicenseDropdown(true)}
            onMouseLeave={() => setLicenseDropdown(false)}
          >
            <Link to="/license" onClick={toggleMobileMenu}>License</Link>
            {licenseDropdown && (
              <ul className="dropdown">
                <li><Link to="/license/free" onClick={toggleMobileMenu}>Free License</Link></li>
                <li><Link to="/license/pro" onClick={toggleMobileMenu}>Pro License</Link></li>
              </ul>
            )}
          </li>
          <li><Link to="/upload" onClick={toggleMobileMenu}>Upload</Link></li>
        </ul>
      </nav>
    </div>
  );
};

export default Navbar;
