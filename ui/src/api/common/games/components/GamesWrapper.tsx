import React from "react";
import {IGamesModel} from "../models/game.model";
import {Col, Container, Row} from "react-bootstrap";
import GameCard from "./GameCard";
import {useStoreContext} from "common/base/stores/store.context";

interface IGamesWrapperProps {
    gamesModel: IGamesModel | null | undefined;
}

const GamesWrapper: React.FC<IGamesWrapperProps> = ({gamesModel}) => {
    const bundle = gamesModel?.bundle;
    const { rootStore: { authStore: { userData } } } = useStoreContext();

    return (
        <Container>
            <h1>{bundle?.['gamesTitle']}</h1>
            <h3>{bundle?.['updatedTitle']} {gamesModel?.getUpdated(userData.locale)}</h3>
            <br/>
            <Row className="gy-3">
                {
                    gamesModel?.games?.map((game, idx) => {
                        return (
                            <Col md={4} lg={3} key={idx}>
                                <GameCard game={game} bundle={bundle} />
                            </Col>
                        )
                    })
                }
            </Row>
        </Container>
    )
}

export default GamesWrapper;