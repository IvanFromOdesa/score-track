import React from "react";
import {
    Bar,
    BarChart,
    CartesianGrid,
    Legend,
    Line,
    LineChart,
    ResponsiveContainer,
    Tooltip,
    XAxis,
    YAxis
} from "recharts";
import {ApiNbaStatsModel} from "../../common/models/stats.model";

interface IPlayerStatLineProps {
    data: StatLine[];
    keys: (keyof ApiNbaStatsModel)[];
    colors?: string[];
}

type StatLine = {
    name: string;
    [key: string]: number | string;
}

const DEFAULT_COLORS = ["#8884d8", "#C8B568", "#90EE90"];

function createColorCycle(colors: string[]) {
    let index = 0;

    return function getNextColor() {
        const color = colors[index];
        index = (index + 1) % colors.length;
        return color;
    };
}

const ApiNbaPlayerStatLineChart: React.FC<IPlayerStatLineProps> = (props) => {

    const colors = props.colors?.length ? props.colors : DEFAULT_COLORS;
    const getNextColor = createColorCycle(colors);

    return (
        <ResponsiveContainer height="100%" width="100%">
            <LineChart data={props.data}>
                <CartesianGrid strokeDasharray="3 3"/>
                <XAxis dataKey="name" padding={{left: 30, right: 30}}/>
                <YAxis/>
                <Tooltip/>
                <Legend/>
                {
                    props.keys.map((k, idx) => {
                        return (
                            <Line
                                type="monotone"
                                dataKey={k}
                                stroke={getNextColor()}
                                activeDot={{r: 8}}
                                key={idx}
                            />
                        );
                    })
                }
            </LineChart>
        </ResponsiveContainer>
    )
}

const ApiNbaPlayerStatBarChart: React.FC<IPlayerStatLineProps> = (props) => {

    const colors = props.colors?.length ? props.colors : DEFAULT_COLORS;
    const getNextColor = createColorCycle(colors);

    return (
        <ResponsiveContainer height="100%" width="100%">
            <BarChart
                data={props.data}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip shared={false} trigger="click" />
                <Legend />
                {
                    props.keys.map((k, idx) =>
                        <Bar
                            dataKey={k}
                            fill={getNextColor()}
                            key={idx}
                        />
                    )
                }
            </BarChart>
        </ResponsiveContainer>
    )
}

export {ApiNbaPlayerStatLineChart, ApiNbaPlayerStatBarChart};