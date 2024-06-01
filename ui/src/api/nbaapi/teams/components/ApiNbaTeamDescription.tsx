import {ApiNbaTeamInfoHelper} from "../models/team.stats.model";
import {Col, Image, Row, Stack} from "react-bootstrap";
import React from "react";

function ApiNbaTeamDescription(infoHelper: ApiNbaTeamInfoHelper | undefined, teamName: string, description: string | undefined | null, srcName: string | undefined) {
    return <Col className="widget-solid p-3" xl={6}>
        <Row className="justify-content-center">
            <Col>
                <Stack gap={3} className="align-items-xl-center">
                    <Image src={infoHelper?.arena} id="nbaapi-arena-img"/>
                    <h5>
                        {infoHelper?.arenaName}
                    </h5>
                </Stack>
            </Col>
            <Col>
                <Row>
                    <h2>{teamName}</h2>
                </Row>
                <Row>
                    <p>{description}</p>
                    <h5><a className="link-secondary" href={infoHelper?.src}>{srcName}</a></h5>
                </Row>
            </Col>
        </Row>
    </Col>;
}

export default ApiNbaTeamDescription;