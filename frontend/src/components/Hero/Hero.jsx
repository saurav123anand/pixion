import React, { useEffect, useState } from "react";
import {fetchRandomMedia } from "../../services/mediaService";// Adjust the path as needed
import "./Hero.css";

const Hero = () => {
  const [mediaUrl, setMediaUrl] = useState("");
  const [author, setAuthor] = useState("");
  const [searchType, setSearchType] = useState("photos");

  useEffect(() => {
    const fetchAndSetMedia = async () => {
      const { mediaUrl, author } = await fetchRandomMedia(searchType);
      setMediaUrl(mediaUrl);
      setAuthor(author);
    };

    console.log("Current Search Type:", searchType);
    fetchAndSetMedia();
  }, [searchType]);

  const handleDropdownChange = (e) => {
    e.preventDefault();
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
            objectFit: "cover",
            width: "100%",
            height: "100%",
          }}
          onError={(e) => {
            e.preventDefault();
            console.error("Error loading video:", e);
            setMediaUrl("https://www.w3schools.com/html/mov_bbb.mp4");
          }}
          src={mediaUrl}
         >
        </video>
      ) : (
        <div
          className="hero-background-image"
          style={{
            backgroundImage: `url(${mediaUrl})`,
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
          <p>
            Explore millions of high-quality images and videos shared by our
            community.
          </p>
          <div className="search-container">
            <select value={searchType} onChange={handleDropdownChange}>
              <option value="photos">Photos</option>
              <option value="videos">Videos</option>
            </select>
            <input
              type="text"
              placeholder={`Search for free ${searchType}`}
            />
            <button>Search</button>
          </div>
        </div>
        <small className="author-name">
          <small style={{ color: "grey" }}>
            {searchType.substring(0, searchType.length - 1)} by{" "}
          </small>
          {author}
        </small>
      </div>
    </div>
  );
};

export default Hero;
