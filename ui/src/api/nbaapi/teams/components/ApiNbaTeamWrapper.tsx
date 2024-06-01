import {ApiNbaTeamModel} from "../models/team.model";
import React from "react";
import {Col, Row} from "react-bootstrap";
import ApiNbaTeamCard from "./ApiNbaTeamCard";
import {Bundle} from "common/base/models/generic.model";

interface IApiNbaTeamWrapperProps {
    teamModel: ApiNbaTeamModel,
    helpText: Bundle | undefined;
}

const ApiNbaTeamWrapper: React.FC<IApiNbaTeamWrapperProps> = ({teamModel, helpText}) => {
    return (
        <Row className="gy-3">
            {
                teamModel.content.map((team, idx) => {
                    return (
                        <Col md={4} lg={3} key={idx}>
                            <ApiNbaTeamCard team={team} helpText={helpText}/>
                        </Col>
                    )
                })
            }
        </Row>
    )
}

export default ApiNbaTeamWrapper;