import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Hero.css";

const Hero = () => {
  const [backgroundImage, setBackgroundImage] = useState("");
  const [author, setAuthor] = useState(""); // To store the author name
  const [searchType, setSearchType] = useState("photos"); // Default to photos

  // Fetch background image and author from backend API
  useEffect(() => {
    const fetchBackgroundImage = async () => {
      try {
        const response = await axios.get("http://localhost:8085/pixion/posts/random");
        // Set the background image URL from the random API response
        setBackgroundImage(response.data.mediaUrL);
        setAuthor(response.data.author); // Set the author's name
      } catch (error) {
        console.error("Error fetching background image:", error);
        // Fallback to a default image if the API call fails
        setBackgroundImage("https://source.unsplash.com/random/1920x1080");
        setAuthor("Unknown Author");
      }
    };

    fetchBackgroundImage();
  }, []); // Empty dependency array means this runs once when the component mounts

  // Handle dropdown change
  const handleDropdownChange = (e) => {
    setSearchType(e.target.value);
  };

  return (
    <div
      className="hero-section"
      style={{
        backgroundImage: `url(${backgroundImage})`, // Dynamically set the background image URL
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
      }}
    >
      <div className="hero-overlay">
        <div className="hero-content">
          <h1>Discover Free Stock Photos & Videos</h1>
          <p>Explore millions of high-quality images and videos shared by our community.</p>
          <div className="search-container">
            {/* Dropdown for selecting photos/videos */}
            <select value={searchType} onChange={handleDropdownChange}>
              <option value="photos">Photos</option>
              <option value="videos">Videos</option>
            </select>

            {/* Search box */}
            <input
              type="text"
              placeholder={`Search for free ${searchType}`} // Dynamic placeholder based on the selected type
            />
            <button>Search</button>
          </div>
        </div>
        {/* Author name positioned at the right bottom corner */}
        <div className="author-name">{author}</div>
      </div>
    </div>
  );
};

export default Hero;
