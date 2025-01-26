// import { useContext, useEffect, useRef,useState } from "react";
// import { MediaContext } from "../../context/MediaContext";
// import "../../../src/App.css";

// function PostDisplay() { 
//     const { posts, loading, error, pageNumber, totalPages, setPageNumber, postType,setPosts } = useContext(MediaContext);
//     const bottomRef = useRef(); // Reference for the bottom element
//     const [transitioning, setTransitioning] = useState(false);

//     useEffect(() => {
//         const observer = new IntersectionObserver(
//             (entries) => {
//                 const entry = entries[0];
//                 if (entry.isIntersecting && !loading && pageNumber < totalPages) {
//                     console.log("Bottom visible. Loading more posts...");
//                     setPageNumber((prevPageNumber) => prevPageNumber + 1);
//                 }
//             },
//             { threshold: 1.0 } // Trigger when the entire element is visible
//         );

//         if (bottomRef.current) {
//             observer.observe(bottomRef.current);
//         }

//         return () => {
//             if (bottomRef.current) {
//                 observer.unobserve(bottomRef.current);
//             }
//         };
//     }, [loading, pageNumber, totalPages]);

//     useEffect(() => {
//                setTransitioning(true); // Start the transition
        
//                 // We keep posts to maintain the current post content while the new posts are being loaded.
//                 setPosts([]);  // Reset posts after the transition ends
//                 setPageNumber(0); // Reset to first page
        
//                 // After a delay (matching the CSS transition duration), we allow new posts to load
//                 const timer = setTimeout(() => {
//                     setTransitioning(false); // End the transition
//                 }, 500); // Match the duration of the CSS transition (500ms)
        
//                 return () => clearTimeout(timer); // Clean up the timer
//             }, [postType, setPosts, setPageNumber]);

//     return (
        
//         <div className="post-display">
//             <h1>Approved Posts</h1>
//             {error && <p className="error">{error}</p>}
//             <div className="post-container">
//                 {posts.length > 0 ? (
//                     posts.map((post, index) => (
//                         <div key={post.postId} className={`post-item ${transitioning ? 'hidden' : ''}`}>
//                             <h3>{post.title}</h3>
//                             <p>{post.content}</p>
//                             {postType === "VIDEO" ? (
//                                 <video
//                                     src={post.mediaUrL}
//                                     alt={post.title}
//                                     controls
//                                     className="media-video"
//                                 />
//                             ) : (
//                                 <img
//                                     src={post.mediaUrL}
//                                     alt={post.title}
//                                     className="media-image"
//                                     loading="lazy"
//                                 />
//                             )}
//                             <p>Location: {post.location}</p>
//                         </div>
//                     ))
//                 ) : (
//                     <p>No posts found</p>
//                 )}
//             </div>
//             {/* Loading Indicator */}
//             <div ref={bottomRef} style={{ textAlign: "center", padding: "10px", fontSize: "16px", color: "gray" }}>
//                 {loading && (
//                     <p>
//                         <span className="spinner"></span> Loading more posts...
//                     </p>
//                 )}
//             </div>
//         </div>
//     );
// }

// export default PostDisplay;

import { useContext, useEffect, useState } from "react";
import { MediaContext } from "../../context/MediaContext";
import InfiniteScroll from "react-infinite-scroll-component";
import Masonry, { ResponsiveMasonry } from "react-responsive-masonry";
import "../../../src/App.css";

function PostDisplay() {
  const {
    posts,
    loading,
    error,
    pageNumber,
    totalPages,
    setPageNumber,
    postType,
    setPosts,
  } = useContext(MediaContext);
  const [transitioning, setTransitioning] = useState(false);

  // Handle post fetching when postType or pageNumber changes
  useEffect(() => {
    setTransitioning(true); // Start the transition

    setPosts([]); // Reset posts after the transition ends
    setPageNumber(0); // Reset to first page

    const timer = setTimeout(() => {
      setTransitioning(false); // End the transition
    }, 500);

    return () => clearTimeout(timer);
  }, [postType, setPosts, setPageNumber]);

  const fetchMorePosts = () => {
    if (pageNumber < totalPages) {
      setPageNumber((prevPageNumber) => prevPageNumber + 1);
    }
  };

  return (
    <div className="post-display">
      <h1 style={{marginBottom:"12px",textTransform:""}}>Trending Free Stock <small style={{textTransform:"capitalize"}}>{postType}</small></h1>
      {error && <p className="error">{error}</p>}

      {/* Infinite Scroll and Masonry Grid */}
      <InfiniteScroll
        dataLength={posts.length}
        next={fetchMorePosts}
        hasMore={pageNumber < totalPages}
        loader={<p style={{ textAlign: "center" }}>Loading...</p>}
        scrollThreshold={0.95}
      >
        <ResponsiveMasonry
          columnsCountBreakPoints={{ 300: 1, 500: 1, 700: 2, 900: 3 }}
        >
          <Masonry gutter="20px">
            {posts.length > 0 ? (
              posts.map((post) => (
                <div
                  key={post.postId}
                  className={`post-item ${transitioning ? "hidden" : ""}`}
                >
                  <h3>{post.title}</h3>
                  <p>{post.content}</p>
                  {postType === "VIDEO" ? (
                    <video
                      src={post.mediaUrL}
                      alt={post.title}
                      controls
                      className="media-video"
                    />
                  ) : (
                    <img
                      src={post.mediaUrL}
                      alt={post.title}
                      className="media-image"
                      loading="lazy"
                    />
                  )}
                  <p>Location: {post.location}</p>
                </div>
              ))
            ) : (
              <p>No posts found</p>
            )}
          </Masonry>
        </ResponsiveMasonry>
      </InfiniteScroll>

      {/* Loading Indicator */}
      {loading && (
        <div style={{ textAlign: "center", padding: "10px", fontSize: "16px", color: "gray" }}>
          <span className="spinner"></span> Loading more posts...
        </div>
      )}
    </div>
  );
}

export default PostDisplay;



