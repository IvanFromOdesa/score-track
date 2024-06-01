import {AuthStore, UserData} from "./auth.store";
import {UiStore} from "./ui.store";
import {Type} from "class-transformer";
import {init} from "../requests/base";
import {Bundle} from "../models/generic.model";
import {action, makeAutoObservable, runInAction} from "mobx";
import {
    SportComponentsMetadataModel,
    SportComponentsMetadataModelImpl
} from "../../../api/common/base/models/sport-components.metadata.model";

export class RootStore {
    authStore: AuthStore = new AuthStore();
    uiStore: UiStore = new UiStore(null);
    _loading: boolean = false;

    constructor() {
        makeAutoObservable(this, {
            init: action
        })
        this.init();
    }

    init = () => {
        this.loading = true;
        init().then(res => {
            runInAction(() => {
                this.authStore = new AuthStore(res.userData);
                this.uiStore = new UiStore(this, res.apiSportComponentsMetadata, res.bundle);
                this.loading = false;
            });
        });
    }

    set loading(loading: boolean) {
        this._loading = loading;
    }

    get loading(): boolean {
        return this._loading;
    }
}

export class InitResponse {
    @Type(() => UserData)
    userData: UserData;
    bundle: Bundle;
    @Type(() => SportComponentsMetadataModelImpl)
    apiSportComponentsMetadata: SportComponentsMetadataModel;

    constructor(userData: UserData, bundle: Bundle, apiSportComponentsMetadata: SportComponentsMetadataModel) {
        this.userData = userData;
        this.bundle = bundle;
        this.apiSportComponentsMetadata = apiSportComponentsMetadata;
    }
}