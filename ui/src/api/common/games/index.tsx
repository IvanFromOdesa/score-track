import React from "react";
import {getGames} from "./requests";
import {useStoreContext} from "common/base/stores/store.context";
import GamesWrapper from "./components/GamesWrapper";
import {useQuery} from "@tanstack/react-query";
import {GAMES_KEY} from "common/base/config/tanstack.query.client";

// TODO: rework
/**
 * Intended to be used as a common interface for any type of API games.
 * @constructor
 */
const Games: React.FC = () => {
    const { rootStore: { uiStore } } = useStoreContext();
    const api = uiStore.getApiBasePath();

    const {data} = useQuery({
        queryKey: [GAMES_KEY.concat(api)],
        queryFn: () => getGames(api)
    });

    return (
        <GamesWrapper gamesModel={data} />
    )
}

export default Games;