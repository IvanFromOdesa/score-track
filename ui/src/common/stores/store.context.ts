import React, {useContext} from "react";
import {RootStore} from "./root.store";

export interface IStoreContext {
    rootStore: RootStore;
}

export const StoreContext = React.createContext<IStoreContext>(
    {} as IStoreContext
);

export const useStoreContext = () => useContext(StoreContext);

