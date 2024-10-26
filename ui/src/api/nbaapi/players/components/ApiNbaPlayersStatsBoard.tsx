import React from "react";
import {useQuery} from "@tanstack/react-query";
import {getPlayerStatsPath} from "../../common/req";
import {getPlayerAvgStatsPerSeason} from "../requests";
import {ApiNbaPlayerStatBarChart, ApiNbaPlayerStatLineChart} from "./ApiNbaPlayerStatCharts";
import {ApiNbaTeamColors} from "../../teams/models/team.model";
import {Bundle} from "common/base/models/generic.model";
import ApiNbaPlayersCustomStatLineChartElement from "./ApiNbaPlayersCustomStatLineChartElement";
import {extractPlayerStats} from "../util/player.stats.util";
import {Col, Row} from "react-bootstrap";
import {Loading} from "../../../../common/load/Loading";

interface IStatsBoardProps {
    id: string | undefined;
    helpText: Bundle | undefined;
    teamColors?: ApiNbaTeamColors;
}

const ApiNbaPlayersStatsBoard: React.FC<IStatsBoardProps> = ({id, helpText, teamColors}) => {
    const {data, isLoading} = useQuery({
        queryKey: [getPlayerStatsPath(id || '')],
        queryFn: () => getPlayerAvgStatsPerSeason(id || ''),
        enabled: id !== undefined
    });

    return (
        <>
            {
                data && <div>
                    <h1 className="mt-4">{helpText?.['avgPerformanceTitle']}</h1>
                    <h2 className="mt-4">{helpText?.['statsPercentagesTitle']}</h2>
                    <p className="mt-4 mb-4">{helpText?.['statsPercentageDescription']}</p>
                    <Row>
                        <Col md={6}>
                            <div className="nbaapi-player-stats-chart">
                                <ApiNbaPlayerStatLineChart
                                    data={extractPlayerStats(data, ['fgp', 'ftp', 'tpp'])}
                                    keys={['fgp', 'ftp', 'tpp']}
                                    colors={Object.values(teamColors || {})}
                                />
                            </div>
                        </Col>
                        <Col md={6} className="mt-sm-4 mt-md-0">
                            <div className="nbaapi-player-stats-chart">
                                <ApiNbaPlayerStatBarChart
                                    data={extractPlayerStats(data, ['plusMinus'])}
                                    keys={['plusMinus']}
                                    colors={Object.values(teamColors || {})}
                                />
                            </div>
                        </Col>
                    </Row>
                    <div>
                        <ApiNbaPlayersCustomStatLineChartElement
                            data={data}
                            helpText={helpText}
                            teamColors={teamColors}
                        />
                    </div>
                </div>
            }
            {
                isLoading && <Loading fixed />
            }
        </>
    )
}

export default ApiNbaPlayersStatsBoard;