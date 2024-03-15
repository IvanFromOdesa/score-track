import React from "react";
import {APIS} from "./apis";
import NbaApi from "./nbaapi";
import {useStoreContext} from "../common/stores/store.context";
import RenewSubscription from "./common/error/RenewSubscription";

const availableApiStrategy = (api: number) => {
    const apiByCode = {
        [APIS.API_NBA.valueOf()]: () => <NbaApi />,
    }
    return apiByCode[api] || (() => <RenewSubscription />);
}

interface IStrategyProps {
    ApiComponent: () => React.ReactElement;
}

// TODO: use composition - create a generic component and pass the strategyComponent to it.
//  In it use a switch component to change apiInUse if there are multiple available apis.
//  Or just simply do it in Api component below
const Consumer: React.FC<IStrategyProps> = ({ ApiComponent }) => <ApiComponent />

const Api: React.FC = () => {
    const { rootStore: { uiStore } } = useStoreContext();

    return (
        <Consumer ApiComponent={availableApiStrategy(uiStore.apiInUse.code)} />
    )
}

export default Api;