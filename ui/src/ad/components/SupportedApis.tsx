import React from "react";
import {WithLoadingProps} from "common/models/generic.model";
import {getApis} from "../requests";
import {TitlePreload} from "common/components/load/Preload";
import ApisList from "common/components/ApisList";
import {useQuery} from "@tanstack/react-query";
import {SUPPORTED_APIS_KEY} from "../../common/config/tanstack.query.client";

const SupportedApis: React.FC<WithLoadingProps> = ({ bundle, loading}) => {

    const {data} = useQuery({
        queryKey: [SUPPORTED_APIS_KEY],
        queryFn: () => getApis()
    })

    return (
        <>
            <br/>
            <div className="text-center mt-auto">
                <TitlePreload loading={loading} size={3}>
                    <h2>{bundle?.['currentSupportApiTitle']}</h2>
                </TitlePreload>
            </div>
            <ApisList apis={data || []} className="justify-content-center align-items-center"/>
        </>
    );
};

export default SupportedApis;