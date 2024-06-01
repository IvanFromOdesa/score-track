import React from "react";
import {useQuery} from "@tanstack/react-query";
import {PLAYERS_LEADERBOARD} from "../../common/req";
import {getLeaderboardPlayers} from "../requests";
import {Col, Image, Row} from "react-bootstrap";
import {roundString} from "common/base/utils/round";
import {htmlFrom} from "common/base/utils/html.parse";
import {useStoreContext} from "common/base/stores/store.context";
import {ApiNbaSportComponentsMetadataContainer} from "../../common/models/nbaapi.sc.metadata.model";
import {DropdownSeasons, DropdownStatCategories} from "../../common/components/Dropdowns";
import {useActiveSeason, useActiveStatCategory} from "../../common/hooks";
import {Loading} from "common/load/Loading";

const ApiNbaPlayersLeaderboard: React.FC = () => {
    const {rootStore: {uiStore: { thisApiSportComponentMetadataContainer } } } = useStoreContext();
    const playersHelpData = thisApiSportComponentMetadataContainer(ApiNbaSportComponentsMetadataContainer)?.helpData.players;
    const seasons = playersHelpData?.seasons;
    const statCategories = playersHelpData?.statCategories;
    const [getActiveSeason, setActiveSeason] = useActiveSeason(seasons?.at(0)?.value);
    const [getActiveStatCategory, setActiveStatCategory] = useActiveStatCategory(statCategories?.at(0)?.value);

    const {data, isLoading} = useQuery({
        queryKey: [PLAYERS_LEADERBOARD, {season: getActiveSeason(), type: getActiveStatCategory()}],
        queryFn: () => getLeaderboardPlayers(getActiveSeason(), getActiveStatCategory())
    });

    const players = data?.data || [];
    const hint = data?.hint;

    function renderLeaderboard() {
        const chunkSize = 5;
        const cols = [];

        const topColors: Record<string, string> = {
            "1": "gold",
            "2": "silver",
            "3": "#CD7F32"
        }

        for (let i = 0; i < players?.length; i += chunkSize) {
            const chunk = players.slice(i, i + chunkSize)
                .map((player, idx) => {
                    const team = player.team;
                    return <Row key={idx} className="align-items-center">
                        <Col sm={1} style={{color: topColors[player.rank] || undefined }}><h3>{player.rank}</h3></Col>
                        <Col sm={3}><Image src={player.imgUrl} width={111} height={64} /></Col>
                        <Col>
                            <Row>
                                <h3>{player.firstName + ' ' + player.lastName}</h3>
                            </Row>
                            <Row>
                                <h5 style={{color: "gray"}}>{team.name}</h5>
                            </Row>
                        </Col>
                        <Col sm={2}>
                            <h4 style={{color: "gray"}}>{roundString(player.valueAvg)}</h4>
                        </Col>
                    </Row>
                });
            cols.push(<Col lg={6} key={i}>{chunk}</Col>);
        }
        return cols;
    }

    return (
        <>
            <Row className="mb-3">
                <Col xs="auto" className="pe-1 me-1">
                    <DropdownSeasons
                        seasons={seasons}
                        activeSeason={getActiveSeason()}
                        setActiveSeason={setActiveSeason}
                    />
                </Col>
                <Col xs="auto" className="ps-1 ms-1">
                    <DropdownStatCategories
                        statCategories={statCategories}
                        activeStatCategory={getActiveStatCategory()}
                        setActiveStatCategory={setActiveStatCategory}
                    />
                </Col>
            </Row>
            {
                isLoading && <Loading />
            }
            <Row>
                <h4 style={{color: 'gray'}}>{htmlFrom(hint?.title)}</h4>
            </Row>
            <Row className="mb-3">
                <h4 className={hint?.className || undefined}>{htmlFrom(hint?.description)}</h4>
            </Row>
            {
                renderLeaderboard()
            }
        </>
    )
}

export default ApiNbaPlayersLeaderboard;