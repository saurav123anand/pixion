import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Hero.css";

const Hero = () => {
  const [mediaUrl, setMediaUrl] = useState(""); // To store the media URL (image/video)
  const [author, setAuthor] = useState(""); // To store the author name
  const [searchType, setSearchType] = useState("photos"); // Default to photos

  // Fetch background image or video and author from backend API
  useEffect(() => {
    const fetchMedia = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8085/pixion/posts/random?type=${
            searchType === "photos" ? "IMAGE" : "VIDEO"
          }`
        );
        console.log(response.data.mediaUrL);
        setMediaUrl(response.data.mediaUrL);
        setAuthor(response.data.author); // Set the author's name
      } catch (error) {
        console.error("Error fetching media:", error);
        // Fallback to a default image if the API call fails
        setMediaUrl("https://source.unsplash.com/random/1920x1080");
        setAuthor("Unknown Author");
      }
    };
    console.log(searchType);
    fetchMedia();
  }, [searchType]); // Re-run the effect whenever searchType changes

  // Handle dropdown change
  const handleDropdownChange = (e) => {
    setSearchType(e.target.value);
  };

  return (
    <div className="hero-section">
      {searchType === "videos" ? (
        <video
          className="hero-background-video"
          autoPlay
          loop
          muted
          playsInline
          style={{
            objectFit: "cover", // Ensure the video covers the entire background
            width: "100%",
            height: "100%",
          }}
        >
          <source src={mediaUrl} type="video/mp4" />
        </video>
      ) : (
        <div
          className="hero-background-image"
          style={{
            backgroundImage: `url(${mediaUrl})`, // Dynamically set the background image URL
            backgroundSize: "cover",
            backgroundPosition: "center",
            backgroundRepeat: "no-repeat",
            width: "100%",
            height: "100%",
          }}
        ></div>
      )}

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
