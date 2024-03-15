import React from "react";
import {getGames} from "./requests";
import './index.css';
import {useStoreContext} from "common/stores/store.context";
import GamesWrapper from "./components/GamesWrapper";
import {useQuery} from "@tanstack/react-query";
import {GAMES_KEY} from "common/config/tanstack.query.client";

const Games: React.FC = () => {
    const { rootStore: { uiStore } } = useStoreContext();
    const apiInUse = uiStore.apiInUse;
    const api = apiInUse.basePath;

    const {data} = useQuery({
        queryKey: [GAMES_KEY.concat(api)],
        queryFn: () => getGames(api)
    });

    return (
        <GamesWrapper gamesModel={data} />
    )
}

export default Games;