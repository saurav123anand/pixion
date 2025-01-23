import { createContext } from "react";
const MediaContext = createContext();

export const MediaProvider=(props)=>{
    return(
        <MediaContext.Provider value={{}}>
            {props.children}
        </MediaContext.Provider>
    )
}