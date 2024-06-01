import {ApiNbaTeam} from "../models/team.model";
import {Bundle} from "../../../../common/base/models/generic.model";
import {Col, Image, Row} from "react-bootstrap";
import React from "react";

function ApiNbaTeamInfo(team: ApiNbaTeam, helpText: Bundle | undefined) {
    const league = team.leagues['standard'];

    return <Col className="widget-solid d-xl-flex justify-content-center align-items-center" xl={5}>
        <Row className="justify-content-center">
            <Col>
                <Image src={team.logo} id="nbaapi-team-logo" />
            </Col>
            <Col>
                <Row>
                    <h3><b>{team.name}</b></h3>
                </Row>
                <Row>
                    <h4>{helpText?.['nicknameTitle']}: <b>{team.nickname}</b></h4>
                </Row>
                <Row>
                    <h4>{helpText?.['cityTitle']}: <b>{team.city}</b></h4>
                </Row>
                <br />
                <Row>
                    <h4>{helpText?.['conferenceTitle']}: <b>{league.conference}</b></h4>
                </Row>
                <Row>
                    <h4>{helpText?.['divisionTitle']}: <b>{league.division}</b></h4>
                </Row>
            </Col>
        </Row>
    </Col>;
}

export default ApiNbaTeamInfo;