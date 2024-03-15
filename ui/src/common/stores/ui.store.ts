import {API_UNDEFINED, Bundle, SportApi} from "../models/generic.model";
import {makeAutoObservable} from "mobx";
import {RootStore} from "./root.store";

export class UiStore {
    rootStore: RootStore | null;
    _apiInUse: SportApi = API_UNDEFINED;
    bundle: Bundle = {};

    constructor(rootStore: RootStore | null, bundle?: Bundle) {
        makeAutoObservable(this);
        this.bundle = bundle || this.bundle;
        this.rootStore = rootStore;
        this.apiInUse = rootStore?.authStore.api;
    }

    set apiInUse(apiInUse: SportApi | undefined) {
        this._apiInUse = apiInUse || API_UNDEFINED;
    }

    get apiInUse(): SportApi {
        return this._apiInUse;
    }
}