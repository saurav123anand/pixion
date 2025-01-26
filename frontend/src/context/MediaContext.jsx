import { createContext, useEffect, useState } from "react";
import { fetchApprovedPostByType } from "../services/mediaService";
export const MediaContext = createContext();

export const MediaProvider = (props) => {
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [pageNumber, setPageNumber] = useState(0);
    const [pageSize] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [postType, setPostType] = useState("IMAGE");

    const loadPosts = async () => {
       // if (loading || pageNumber >= totalPages) return; // prevent unnecessary API calls
        setLoading(true);
        setError(null);

        console.log(`Fetching posts for postType: ${postType}, pageNumber: ${pageNumber}`);
        try {
            const response = await fetchApprovedPostByType(postType, pageNumber, pageSize);
            console.log("API response:", response);
            // setPosts((prevPosts) => [...prevPosts, ...response.posts]);
            setPosts((prevPosts) => {
                const newPosts = response.posts.filter(
                  (post) => !prevPosts.some((prevPost) => prevPost.postId === post.postId)
                );
                return [...prevPosts, ...newPosts]; // Append only unique posts
              });
            setTotalPages(response.totalPages);
        } catch (error) {
            console.error("Error fetching posts:", error);
            setError(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        // Add a console log to verify useEffect is being triggered when postType or pageNumber changes
        console.log("useEffect triggered:", postType, pageNumber);
        loadPosts();
    }, [postType, pageNumber]);

    return (
        <MediaContext.Provider
            value={{
                posts,
                loading,
                error,
                pageNumber,
                totalPages,
                setPageNumber,
                postType,
                setPostType,
                loadPosts,
                setPosts
            }}
        >
            {props.children}
        </MediaContext.Provider>
    );
};

