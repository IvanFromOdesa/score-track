import React from "react";
import {IGameModel} from "../models/game.model";
import {GenericComponentProps} from "common/base/models/generic.model";
import SportComponentCard from "../../base/SportComponentCard";

interface IGameCardProps extends GenericComponentProps {
    game: IGameModel,
}

const GameCard: React.FC<IGameCardProps> = ({game, bundle}) => {
    const img = game.winner?.logo || game.league.logo;
    const info = bundle?.['dateTitle'] + ":" + game.getTimestamp();

    return (
        <SportComponentCard
            img={img}
            title={game.getTitle()}
            info={info}
            badges={game.getKeywords()}
            btnTitle={bundle?.['exploreBtnTitle']}
        />
    )
};

export default GameCard;