import React, {useState} from "react";
import {Button, Image} from "react-bootstrap";
import {ApiNbaPlayerStatsData} from "../models/player.stats.model";
import {Bundle} from "common/base/models/generic.model";
import {ApiNbaStatCategory} from "../../common/models/stat.category.model";
import {extractPlayerStats} from "../util/player.stats.util";
import {ApiNbaPlayerStatLineChart} from "./ApiNbaPlayerStatCharts";
import {ApiNbaStatsModel} from "../../common/models/stats.model";
import {PlayerStatCategoriesModal} from "./ApiNbaPlayerStatCategoriesModal";
import noChart from "../../../../assets/img_warning.webp";
import {ApiNbaTeamColors} from "../../teams/models/team.model";

interface IApiNbaPlayersCustomStatLineChartElementProps {
    data: ApiNbaPlayerStatsData;
    helpText: Bundle | undefined;
    teamColors?: ApiNbaTeamColors;
}

const ApiNbaPlayersCustomStatLineChartElement: React.FC<IApiNbaPlayersCustomStatLineChartElementProps> = ({data, helpText, teamColors}) => {
    const [modalShow, setModalShow] = useState(false);
    const [selectedValues, setSelectedValues] = useState<ApiNbaStatCategory[]>([]);
    const hideModal = () => setModalShow(false);

    const getKeys = () => selectedValues.map(s => s.value) as (keyof ApiNbaStatsModel)[];

    return (
        <>
            <h2 className="mt-4">{helpText?.['customStatsChartTitle']}</h2>
            <p className="mt-4 mb-4">{helpText?.['customStatsChartDescription']}</p>
            <Button variant="primary" onClick={() => setModalShow(true)}>
                {helpText?.['customStatsChartModalOpenButtonTitle']}
            </Button>
            {PlayerStatCategoriesModal(
                modalShow,
                hideModal,
                selectedValues,
                (values) => setSelectedValues(values),
                data.groupedStatCategories,
                helpText
            )}
            <div className="mt-4">
                {
                    selectedValues.length !== 0 ?
                        <div className="nbaapi-player-stats-chart">
                            <ApiNbaPlayerStatLineChart
                                data={extractPlayerStats(data, getKeys())}
                                keys={getKeys()}
                                colors={Object.values(teamColors || {})}
                            />
                        </div> :
                        <div className="text-center">
                            <Image src={noChart} width="25%" height="25%" />
                            <h4 className="mt-2">{helpText?.['customStatsChartNoStatsSelected']}</h4>
                        </div>
                }
            </div>
        </>
    )
}

export default ApiNbaPlayersCustomStatLineChartElement;