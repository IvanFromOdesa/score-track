import React from "react";
import {Row} from "react-bootstrap";

function ApiNbaTeamStatsChartElement(pieChart: React.JSX.Element,
                                     fgDescription: React.JSX.Element,
                                     made: React.JSX.Element,
                                     attempted: React.JSX.Element,
                                     percentage: React.JSX.Element) {
    return (
        <div className="widget-solid d-flex flex-column h-100 p-3">
            <Row>
                {pieChart}
            </Row>
            <Row>
                <p>
                    {fgDescription}
                </p>
            </Row>
            <br />
            <div className="mt-auto">
                <Row>
                    <h3>{made}</h3>
                </Row>
                <Row>
                    <h3>{attempted}</h3>
                </Row>
                <Row>
                    <h3>{percentage}</h3>
                </Row>
            </div>
        </div>
    );
}

export default ApiNbaTeamStatsChartElement;