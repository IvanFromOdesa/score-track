import {ApiNbaPlayerStatsData} from "../models/player.stats.model";
import {ApiNbaStatsModel} from "../../common/models/stats.model";
import {ApiNbaStatCategory} from "../../common/models/stat.category.model";

export function extractPlayerStats(data: ApiNbaPlayerStatsData, fields: (keyof ApiNbaStatsModel)[]) {
    type OutputData = {
        name: string;
        [key: string]: number | string;
    };

    return data.seasons
        .sort((a, b) => a.value.localeCompare(b.value))
        .map(season => {
            const seasonData = data.data[season.value];
            const result: OutputData = { name: season.value };

            fields.forEach(field => {
                result[field] = seasonData[field];
            });

            return result;
        });
}

export function extractStatCategory(statCategories: ApiNbaStatCategory[], stat: string) {
    return statCategories.find(s => s.value === stat);
}