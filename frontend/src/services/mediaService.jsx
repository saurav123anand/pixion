import axios from "axios";

export const fetchRandomMedia = async (searchType) => {
  try {
    const response = await axios.get(
      `http://localhost:8085/pixion/posts/random?type=${
        searchType === "photos" ? "IMAGE" : "VIDEO"
      }`
    );

    const mediaUrl =
      response.data.mediaUrL ||
      (searchType === "photos"
        ? "https://source.unsplash.com/random/1920x1080"
        : "https://www.w3schools.com/html/mov_bbb.mp4");

    const author = response.data.author || "Unknown Author";

    return { mediaUrl, author };
  } catch (error) {
    console.error("Error fetching media:", error);
    const fallbackUrl =
      searchType === "photos"
        ? "https://source.unsplash.com/random/1920x1080"
        : "https://www.w3schools.com/html/mov_bbb.mp4";

    return { mediaUrl: fallbackUrl, author: "Unknown Author" };
  }
};
export const fetchApprovedPostByType = async (postType,pageNumber,pageSize) => {
    const url=`http://localhost:8085/pixion/posts/approved/filter?postType=${postType}&pageNumber=${pageNumber}&pageSize=${pageSize}`;
    try{
       const response=await axios.get(url);
       console.log("Response:",response);
       return response.data;
    }
    catch(error){
      console.error("Error fetching media:", error);
    }
}
