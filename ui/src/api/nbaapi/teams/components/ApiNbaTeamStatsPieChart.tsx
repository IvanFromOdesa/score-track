import {getRenderActiveShape, Payload, Tip} from "../../../../common/recharts/pie";
import React, {useCallback, useState} from "react";
import {Col} from "react-bootstrap";
import {Pie, PieChart, ResponsiveContainer} from "recharts";

function ApiNbaTeamStatsPieChart(data: Payload[], tip: Tip, color: string) {
    const [activeIndex, setActiveIndex] = useState<number>(0);
    const onPieEnter = useCallback(
        (_: any, index: number) => {
            setActiveIndex(index);
        },
        [setActiveIndex]
    );

    return <Col>
        <div id="nbaapi-team-stats-chart">
            <ResponsiveContainer height="100%" width="100%">
                <PieChart>
                    <Pie
                        activeIndex={activeIndex}
                        activeShape={getRenderActiveShape(tip)}
                        data={data}
                        cx={"50%"}
                        cy={"50%"}
                        innerRadius={"50%"}
                        outerRadius={"55%"}
                        fill={color}
                        dataKey="value"
                        onMouseEnter={onPieEnter}
                    />
                </PieChart>
            </ResponsiveContainer>
        </div>
    </Col>;
}

export default ApiNbaTeamStatsPieChart;