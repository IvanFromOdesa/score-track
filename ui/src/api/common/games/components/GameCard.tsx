import React from "react";
import {IGameModel} from "../models/game.model";
import {Badge, Button, Card} from "react-bootstrap";
import {GenericComponentProps} from "common/models/generic.model";

interface IGameCardProps extends GenericComponentProps {
    game: IGameModel,
}

const GameCard: React.FC<IGameCardProps> = ({game, bundle}) => {
    const img = game.winner?.logo || game.league.logo;

    return (
        <>
            <Card className="game-card h-100">
                <div className="text-center m-2">
                    <Card.Img variant="top" src={img} style={{width: '120px', height:'120px'}} loading={"lazy"}/>
                </div>
                <Card.Body className="game-card-body d-flex flex-column">
                    <Card.Title className="text-center">
                        <h4>{game.getTitle()}</h4>
                    </Card.Title>
                    &nbsp;
                    <h5>
                        {bundle?.['dateTitle']}: {game.getTimestamp()}
                    </h5>
                    <h3>
                        {game.getKeywords().map((name, i) => <Badge bg="primary" key={i} style={{marginRight: '5px', marginBottom: '3px'}}>{name}</Badge>)}
                    </h3>
                    <div className="text-center mt-auto">
                        <Button variant="dark">{bundle?.['exploreBtnTitle']}</Button>
                    </div>
                </Card.Body>
            </Card>
        </>
    )
};

export default GameCard;