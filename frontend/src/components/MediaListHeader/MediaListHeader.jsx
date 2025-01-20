import { useState } from 'react';
import './MediaListHeader.css';
function MediaListHeader() {
  const [activeButton, setActiveButton] = useState(0);
  const handleClick = (index) => {
    setActiveButton(index);
  }
  return (
    <div className='media-list-header'>
      {['Home', 'Videos', 'Leaderboard'].map((button, index) => (
        <button
          key={index}
          className={`btn ${activeButton === index ? 'active' : ''}`}
          onClick={() => handleClick(index)}
        >
          {button}
        </button>
      ))}
    </div>
  );
}
export default MediaListHeader;