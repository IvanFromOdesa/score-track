import {ApiNbaTeam} from "../models/team.model";
import React from "react";
import SportComponentCard from "../../../common/base/SportComponentCard";
import {Bundle} from "common/base/models/generic.model";
import {useNavigate} from "react-router-dom";
import {API_NBA_TEAM_STATS_PAGE} from "common/base/routes/routes";

interface IApiNbaTeamCardProps {
    team: ApiNbaTeam;
    helpText: Bundle | undefined;
}

const ApiNbaTeamCard: React.FC<IApiNbaTeamCardProps> = ({team, helpText}) => {
    const league = team.leagues['standard'];
    const navigate = useNavigate();

    const leagueInfo = <>
        <h5>{helpText?.['conferenceTitle']}: {league.conference}</h5>
        <h5>{helpText?.['divisionTitle']}: {league.division}</h5>
        <br />
    </>;

    return (
        <SportComponentCard
            img={team.logo}
            title={team.name}
            infoJSX={() => leagueInfo}
            badges={[]}
            btnOnClick={() => navigate(API_NBA_TEAM_STATS_PAGE.replace(':id', team.id), {state: team})}
        />
    )
}

export default ApiNbaTeamCard;