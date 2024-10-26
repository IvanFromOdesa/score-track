import React from "react";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {PLAYERS} from "../../common/req";
import {getPlayerById} from "../requests";
import {Card, Col, Container, Image, Row} from "react-bootstrap";
import './index.css';
import {useActiveSeason} from "../../common/hooks";
import {useStoreContext} from "common/base/stores/store.context";
import ApiNbaPlayersStatsBoard from "../components/ApiNbaPlayersStatsBoard";

const ApiNbaPlayerPage: React.FC = () => {
    const { rootStore: { uiStore: { thisApiSportComponentHelpText } } } = useStoreContext();
    const helpText = thisApiSportComponentHelpText('players');
    const {id} = useParams();

    const {data} = useQuery({
        queryKey: [PLAYERS, id],
        queryFn: () => getPlayerById(id || ''),
        enabled: id !== undefined
    });

    const player = data?.data;
    const [getActiveSeason] = useActiveSeason(data?.seasons?.at(0)?.value);
    const currentSeason = player?.teamBySeason[getActiveSeason()];
    const infoHelper = currentSeason?.infoHelper;

    return (
        <>
            {
                player && <Container className="nbaapi-player-page">
                    <Row className="nbaapi-player-header" style={{ backgroundColor: infoHelper?.colors.primary }}>
                        <Col xs={12} md={4} className="text-center">
                            <Image src={player.imgUrl} roundedCircle className="nbaapi-player-image" />
                        </Col>
                        <Col xs={12} md={8} className="nbaapi-player-info">
                            <h1>{player.firstname} {player.lastname}</h1>
                            <h3>{player.leagues.standard.pos.uiText} | #{player.leagues.standard.jersey}</h3>
                            <h4>Team: {currentSeason?.name}</h4>
                        </Col>
                    </Row>
                    <Row className="nbaapi-player-details">
                        <Col xs={12}>
                            <Card>
                                <Card.Body>
                                    <Row>
                                        <Col xs={12} md={6}>
                                            <p><strong>{helpText?.['playersBirthDataTitle']}:</strong> {player.birth.date}</p>
                                            <p><strong>{helpText?.['playersBirthDataCountryTitle']}:</strong> {player.birth.country}</p>
                                            <p><strong>{helpText?.['playersHeightTitle']}:</strong> {player.height.feets}{helpText?.['playersHeightFtTitle']} {player.height.inches}{helpText?.['playersHeightInTitle']} ({player.height.meters}{helpText?.['playersHeightMTitle']})</p>
                                            <p><strong>{helpText?.['playersWeightTitle']}:</strong> {player.weight.pounds}{helpText?.['playersWeightLbsTitle']} ({player.weight.kilograms}{helpText?.['playersWeightKgTitle']})</p>
                                        </Col>
                                        <Col xs={12} md={6}>
                                            <p><strong>{helpText?.['playersNbaStartCareerTitle']}:</strong> {player.nba.start}</p>
                                            <p><strong>{helpText?.['playersYearsProTitle']}:</strong> {player.nba.pro}</p>
                                            <p><strong>{helpText?.['playersCollegeTitle']}:</strong> {player.college}</p>
                                            <p><strong>{helpText?.['playersAffiliationTitle']}:</strong> {player.affiliation}</p>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                    <Row>
                        <ApiNbaPlayersStatsBoard
                            id={id}
                            helpText={helpText}
                            teamColors={infoHelper?.colors}
                        />
                    </Row>
                </Container>
            }
        </>
    );
}

export default ApiNbaPlayerPage;