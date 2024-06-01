import React from "react";
import {Col, Row} from "react-bootstrap";
import {Bundle} from "../../../../common/base/models/generic.model";
import {ApiNbaTeamStatsModel} from "../models/team.stats.model";
import ApiNbaTeamStatsPieChart from "./ApiNbaTeamStatsPieChart";
import ApiNbaTeamStatsChartElement from "./ApiNbaTeamStatsChartElement";

const StatRow: React.FC<{title: string | undefined, value: any}> = ({ title, value }) => (
    <Row>
        <h3>{title}: <b>{value}</b></h3>
    </Row>
);

function renderStats(statItems: {title: string | undefined, value: number | string | undefined}[]) {
    return statItems.map((item, index) => (
        <StatRow key={index} title={item.title} value={item.value}/>
    ));
}

export function FgStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined, color: string) {
    const pieChart = ApiNbaTeamStatsPieChart([
        {
            name: helpText?.['fgmTitle'],
            value: stats?.fgm
        },
        {
            name: helpText?.['fgaTitle'],
            value: stats?.fga
        },
    ], {
        mainLine: helpText?.['fieldGoalsTitle'],
        subLine: helpText?.['percentageTitle']
    }, color);

    const fgDescription = <>{helpText?.['fgDescription']}</>;

    const made = <>{helpText?.['fgmTitle']}: <b>{stats?.fgm}</b></>;
    const attempted = <>{helpText?.['fgaTitle']}: <b>{stats?.fga}</b></>;
    const percentage = <>{helpText?.['fgpTitle']}: <b>{stats?.fgp}</b></>;

    return ApiNbaTeamStatsChartElement(pieChart, fgDescription, made, attempted, percentage);
}

export function FtStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined, color: string) {
    const pieChart = ApiNbaTeamStatsPieChart([
        {
            name: helpText?.['ftmTitle'],
            value: stats?.ftm
        },
        {
            name: helpText?.['ftaTitle'],
            value: stats?.fta
        },
    ], {
        mainLine: helpText?.['freeThrowsTitle'],
        subLine: helpText?.['percentageTitle']
    }, color);

    const fgDescription = <>{helpText?.['ftDescription']}</>;

    const made = <>{helpText?.['ftmTitle']}: <b>{stats?.ftm}</b></>;
    const attempted = <>{helpText?.['ftaTitle']}: <b>{stats?.fta}</b></>;
    const percentage = <>{helpText?.['ftpTitle']}: <b>{stats?.ftp}</b></>;

    return ApiNbaTeamStatsChartElement(pieChart, fgDescription, made, attempted, percentage);
}

export function TpStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined, color: string) {
    const pieChart = ApiNbaTeamStatsPieChart([
        {
            name: helpText?.['tpmTitle'],
            value: stats?.tpm
        },
        {
            name: helpText?.['tpaTitle'],
            value: stats?.tpa
        },
    ], {
        mainLine: helpText?.['threePointsTitle'],
        subLine: helpText?.['percentageTitle']
    }, color);

    const fgDescription = <>{helpText?.['tpDescription']}</>;

    const made = <>{helpText?.['tpmTitle']}: <b>{stats?.tpm}</b></>;
    const attempted = <>{helpText?.['tpaTitle']}: <b>{stats?.tpa}</b></>;
    const percentage = <>{helpText?.['tppTitle']}: <b>{stats?.tpp}</b></>;

    return ApiNbaTeamStatsChartElement(pieChart, fgDescription, made, attempted, percentage);
}

export function ReboundsStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined) {
    const statItems = [
        { title: helpText?.['offRebTitle'], value: stats?.offReb },
        { title: helpText?.['defRebTitle'], value: stats?.defReb },
        { title: helpText?.['totRebTitle'], value: stats?.totReb },
    ];

    return <Col xl={6} className="widget-solid gap-3 p-3">
        <h2 className="mb-4">
            {helpText?.['reboundsTitle']}
        </h2>
        <p>
            {helpText?.['rbDescription']}
        </p>
        <br />
        {renderStats(statItems)}
    </Col>;
}

export function OffensiveStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined) {
    const statItems = [
        { title: helpText?.['assistsTitle'], value: stats?.assists },
        { title: helpText?.['turnoversTitle'], value: stats?.turnovers },
        { title: helpText?.['fastBreakPointsTitle'], value: stats?.fastBreakPoints },
        { title: helpText?.['pointsInPaintTitle'], value: stats?.pointsInPaint },
        { title: helpText?.['pointsOffTurnoversTitle'], value: stats?.pointsOffTurnovers },
        { title: helpText?.['secondChancePointsTitle'], value: stats?.secondChancePoints },
        { title: helpText?.['biggestLeadTitle'], value: stats?.biggestLead },
    ];

    return <Col xl={5} className="widget-solid offset-sm-0 offset-xl-1 p-3">
        <h2 className="mb-4">
            {helpText?.['offTitle']}
        </h2>
        {renderStats(statItems)}
    </Col>;
}

export function DefensiveStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined) {
    const statItems = [
        { title: helpText?.['stealsTitle'], value: stats?.steals },
        { title: helpText?.['blocksTitle'], value: stats?.blocks },
    ];

    return <Col xl={5} className="widget-solid mt-xl-3 p-3 offset-sm-0 offset-xl-1">
        <h2 className="mb-4">
            {helpText?.['defTitle']}
        </h2>
        {renderStats(statItems)}
    </Col>;
}

export function OtherStats(helpText: Bundle | undefined, stats: ApiNbaTeamStatsModel | undefined) {
    const statItems = [
        { title: helpText?.['pFoulsTitle'], value: stats?.pFouls },
        { title: helpText?.['plusMinusTitle'], value: stats?.plusMinus },
        { title: helpText?.['gamesTitle'], value: stats?.games },
        { title: helpText?.['longestRunTitle'], value: stats?.longestRun }
    ];

    return <Col xl={6} className="widget-solid mt-3 p-3 gap-3">
        <h2 className="mb-4">{helpText?.['otherStatsTitle']}</h2>
        {renderStats(statItems)}
    </Col>;
}