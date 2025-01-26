import { MediaProvider } from "../../context/MediaContext";
import Hero from "../Hero/Hero";
import MediaListHeader from "../MediaListHeader/MediaListHeader";
import MediaSection from "../MediaSection/MediaSection";

function Home() {
    return (
        <div className="home">
            <Hero />
            <MediaProvider>
                <MediaSection />
            </MediaProvider>

        </div>
    );
}
export default Home;