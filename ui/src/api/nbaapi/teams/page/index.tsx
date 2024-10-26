import React from "react";
import {useLocation, useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {getTeamStatsPath} from "../../common/req";
import {getTeamStats} from "../requests";
import {ApiNbaTeam} from "../models/team.model";
import {Col, Container, Row} from "react-bootstrap";
import './index.css';
import {useStoreContext} from "common/base/stores/store.context";
import ApiNbaTeamDescription from "../components/ApiNbaTeamDescription";
import ApiNbaTeamInfo from "../components/ApiNbaTeamInfo";
import ApiNbaTeamStatsTable from "../components/ApiNbaTeamStatsTable";
import {
    DefensiveStats,
    FgStats,
    FtStats,
    OffensiveStats,
    OtherStats,
    ReboundsStats,
    TpStats
} from "../components/ApiNbaTeamStatsWidgets";
import {ApiNbaSportComponentsMetadataContainer} from "../../common/models/nbaapi.sc.metadata.model";
import {useActiveSeason} from "../../common/hooks";
import {DropdownSeasons} from "../../common/components/Dropdowns";
import Hint from "common/base/components/Hint";

const ApiNbaTeamPage: React.FC = () => {
    const {id} = useParams();
    const { state } = useLocation();
    const team = state as ApiNbaTeam;
    const { rootStore: { uiStore: { thisApiSportComponentHelpText, thisApiSportComponentMetadataContainer } } } = useStoreContext();
    const helpText = thisApiSportComponentHelpText('teams');

    const {data} = useQuery({
        queryKey: [getTeamStatsPath(id || '', team.code)],
        queryFn: () => getTeamStats(id || '', team.code),
        enabled: id !== undefined
    });

    const [getActiveSeason, setActiveSeason] = useActiveSeason(data?.seasons.at(0)?.value);

    const stats = data?.data[getActiveSeason()];
    const infoHelper = data?.infoHelper;
    const avgTeamStats = thisApiSportComponentMetadataContainer(ApiNbaSportComponentsMetadataContainer)?.helpData.teams.data[getActiveSeason()];

    function getStyle() {
        return {
            backgroundImage: `url(${team.logo})`,
            backgroundSize: '50% 50%',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            backgroundAttachment: 'fixed'
        }
    }

    return (
        <>
            {
                team && <Container id='nbaapi-team-stats-bg' className='bg-blur-filter'
                                   style={getStyle() as React.CSSProperties}>
                    <div id='nbaapi-team-stats-content'>
                        <Row className="gap-5">
                            {ApiNbaTeamInfo(team, helpText)}
                            {ApiNbaTeamDescription(infoHelper, team.name, data?.description, helpText?.['wikiSrcTitle'])}
                        </Row>
                    </div>
                    <br/>
                    <div id="nbaapi-team-stats-content-title" className="d-flex flex-row">
                        <h1>
                            {helpText?.['statsTitle']}
                        </h1>
                        &nbsp;
                        <div className="mt-1">
                            <DropdownSeasons
                                seasons={data?.seasons}
                                activeSeason={getActiveSeason()}
                                setActiveSeason={setActiveSeason}
                            />
                        </div>
                    </div>
                    <br/>
                    <div id='nbaapi-team-stats-content-analytics'>
                        <Row className="d-flex">
                            <Col xl={4} className="mb-3">
                                {FgStats(helpText, stats, infoHelper?.colors.primary || 'black')}
                            </Col>
                            <Col xl={4} className="mb-3">
                                {FtStats(helpText, stats, infoHelper?.colors.primary || 'black')}
                            </Col>
                            <Col xl={4} className="mb-3">
                                {TpStats(helpText, stats, infoHelper?.colors.primary || 'black')}
                            </Col>
                        </Row>
                        <br/>
                        <Row className="gap-5 gap-xl-0">
                            <Row className="justify-content-center d-flex gap-3 gap-xl-0">
                                {ReboundsStats(helpText, stats)}
                                <br/>
                                {OffensiveStats(helpText, stats)}
                            </Row>
                            <Row className="justify-content-center d-flex gap-3 gap-xl-0">
                                {OtherStats(helpText, stats)}
                                <br/>
                                {DefensiveStats(helpText, stats)}
                            </Row>
                        </Row>
                    </div>
                    <br/>
                    <div id="nbaapi-team-stats-content-table" className="widget p-3">
                        <h1>{helpText?.['statsTableTitle']} &nbsp;
                            <Hint text={helpText?.['statsTableHint']} size={30} />
                        </h1>
                        <Row>
                            {ApiNbaTeamStatsTable(stats, avgTeamStats, helpText)}
                        </Row>
                    </div>
                </Container>
            }
        </>
    )
}

export default ApiNbaTeamPage;