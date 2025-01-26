// import { useContext, useState } from 'react';
// import './MediaListHeader.css';
// import { MediaContext } from '../../context/MediaContext';
// function MediaListHeader() {
//   const [activeButton, setActiveButton] = useState(0);
//   const {setPostType,posts} = useContext(MediaContext);
//   const handleClick = (index) => {
//     setActiveButton(index);
//     setPostType(index === 0 ? 'IMAGE' : index === 1 ? 'VIDEO' : '')
//   }
//   return (
//     <div className='media-list-header'>
//       {['Home', 'Videos', 'Leaderboard'].map((button, index) => (
//         <button
//           key={index}
//           className={`btn ${activeButton === index ? 'active' : ''}`}
//           onClick={() => handleClick(index, button)}
//         >
//           {button}
//         </button>
//       ))}
//     </div>
//   );
// }
// export default MediaListHeader;

import { useContext, useState } from 'react';
import './MediaListHeader.css';
import { MediaContext } from '../../context/MediaContext';

function MediaListHeader() {
  const [activeButton, setActiveButton] = useState(0);
  const { setPostType } = useContext(MediaContext);

  const handleClick = (index) => {
    setActiveButton(index);
    setPostType(index === 0 ? 'IMAGE' : index === 1 ? 'VIDEO' : '');
  };

  return (
    <div className='media-list-header'>
      {['Home', 'Videos', 'Leaderboard'].map((button, index) => (
        <button
          key={index}
          className={`btn ${activeButton === index ? 'active' : ''}`}
          onClick={() => handleClick(index)} // Only pass index
        >
          {button}
        </button>
      ))}
    </div>
  );
}

export default MediaListHeader;
