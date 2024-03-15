import React, {useRef} from "react";
import {IStoreContext, StoreContext} from "./store.context";
import {RootStore} from "./root.store";

export const StoreProvider: React.FC<React.PropsWithChildren<{}>> = ({ children }) => {
    const storeContext: IStoreContext = {
        rootStore: new RootStore()
    };

    const store = useRef(storeContext);

    return (
        <StoreContext.Provider value={store.current}>
            {children}
        </StoreContext.Provider>
    );
}