import React, {useState} from "react";
import {useQuery} from "@tanstack/react-query";
import {getTeams} from "./requests";
import {TEAMS} from "../common/req";
import {BasePagination} from "common/pagination/BasePagination";
import ApiNbaTeamWrapper from "./components/ApiNbaTeamWrapper";
import {Loading} from "common/load/Loading";
import {Col, Container, Row} from "react-bootstrap";
import UpdateTooltip from "../common/components/UpdateTooltip";
import {useStoreContext} from "common/base/stores/store.context";

const ApiNbaTeams: React.FC = () => {
    const { rootStore: { uiStore: { thisApiSportComponentHelpText, thisApiSportComponentMetadata }, authStore: { userData: { locale } } } } = useStoreContext();
    const teamSportComponentHelpText = thisApiSportComponentHelpText('teams');
    const updated = thisApiSportComponentMetadata('teams')?.updated;

    const [activePage, setActivePage] = useState<number>(0);

    const {data, isLoading} = useQuery({
        queryKey: [TEAMS, { activePage }],
        queryFn: () => getTeams(activePage)
    });

    return (
        <Container>
            <Row>
                <Col>
                    <h1>{teamSportComponentHelpText?.['teamsTitle']}</h1>
                </Col>
                <Col>
                    <UpdateTooltip
                        updateText={teamSportComponentHelpText?.['updatedTitle']}
                        updated={updated}
                        locale={locale}
                    />
                </Col>
            </Row>
            <br/>
            {
                data && <BasePagination
                    activePage={activePage}
                    pageSetter={(n) => setActivePage(n)}
                    pagination={data}
                    render={() => <ApiNbaTeamWrapper
                        helpText={teamSportComponentHelpText}
                        teamModel={data}
                    />}
                />
            }
            {
                isLoading && <Loading />
            }
        </Container>
    )
}

export default ApiNbaTeams;