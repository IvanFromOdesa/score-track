import React from "react";
import ApiNbaTeams from "./teams";
import {useStoreContext} from "../../common/base/stores/store.context";
import ApiNbaPlayers from "./players";

const NbaApi: React.FC = () => {
    const { rootStore : { uiStore} } = useStoreContext();

    // TODO: finish
    return (
        <>
            {
                uiStore.isThisApiSportComponentMetadataAccessible('players') && <ApiNbaPlayers/>
            }
            <br/>
            <br/>
            {
                uiStore.isThisApiSportComponentMetadataAccessible('teams') && <ApiNbaTeams/>
            }
            {/*<Games />*/}
        </>
    )
}

export default NbaApi;