import React, {useMemo} from "react";
import {Bundle} from "common/base/models/generic.model";
import {ApiNbaTeamStatsModel} from "../models/team.stats.model";
import {createColumns} from "common/table/table";
import {getCoreRowModel, useReactTable} from "@tanstack/react-table";
import {Table} from "react-bootstrap";
import {TableComponent} from "common/table/TableComponent";

function ApiNbaTeamStatsTable(stats: ApiNbaTeamStatsModel | undefined, avgStats: ApiNbaTeamStatsModel | undefined, helpText: Bundle | undefined) {
    function getShortTitle(target: string) {
        return helpText?.[`${target}ShortTitle`];
    }

    const data = useMemo(() => stats ? [stats] : [], [stats]);

    function getColumns() {
        const apiNbaTeamStatsModel = data[0];

        function getColor(k: string, v: any) {
            if (avgStats) {
                const avgStat = Number(avgStats[k as keyof ApiNbaTeamStatsModel]);
                return isNaN(avgStat) ? undefined : v > avgStat ? 'green' : avgStat === v ? undefined : 'red';
            }
        }

        const columnProps = {
            getHeader: (k: string) => getShortTitle(k) || '',
            data: apiNbaTeamStatsModel ? apiNbaTeamStatsModel : {},
            cellStyle: (k: string, v: any) => <div style={{color: getColor(k, v)}}>{v}</div>
        };

        return createColumns(columnProps);
    }

    const columns = useMemo(() => getColumns(), [data]); // eslint-disable-line

    const table = useReactTable({columns, data, getCoreRowModel: getCoreRowModel()});

    return (
        <Table responsive>
            {TableComponent(table)}
        </Table>
    )
}

export default ApiNbaTeamStatsTable;