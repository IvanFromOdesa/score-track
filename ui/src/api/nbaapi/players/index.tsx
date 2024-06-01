import React from "react";
import {Col, Container, Row} from "react-bootstrap";
import {useStoreContext} from "common/base/stores/store.context";
import ApiNbaPlayersLeaderboard from "./components/ApiNbaPlayersLeaderboard";
import UpdateTooltip from "../common/components/UpdateTooltip";
import Hint from "common/base/components/Hint";

const ApiNbaPlayers: React.FC = () => {
    const { rootStore: { uiStore: { thisApiSportComponentHelpText, thisApiSportComponentMetadata }, authStore: { userData: { locale } } } } = useStoreContext();
    const helpText = thisApiSportComponentHelpText('players');
    const updated = thisApiSportComponentMetadata('players')?.updated;

    return (
        <Container>
            <Row>
                <Col>
                    <h1>{helpText?.['playersTitle']}</h1>
                </Col>
                <Col>
                    <UpdateTooltip
                        updateText={helpText?.['updatedTitle']}
                        updated={updated}
                        locale={locale}
                    />
                </Col>
            </Row>
            <br/>
            <Row>
                <h2>{helpText?.['playersTopTitle']}&nbsp;
                    <Hint text={helpText?.['seasonLeadersHint']} size={25} />
                </h2>
            </Row>
            <Row>
                <ApiNbaPlayersLeaderboard/>
            </Row>
        </Container>
    );
}

export default ApiNbaPlayers;