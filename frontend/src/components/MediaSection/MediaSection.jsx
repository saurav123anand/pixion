import MediaListHeader from "../MediaListHeader/MediaListHeader";
import PostDisplay from "../PostDisplay/PostDisplay";

function MediaSection() {
  return (
    <section className="media-section">
        <MediaListHeader/>
        <PostDisplay/>
    </section>
  );
}
export default MediaSection;