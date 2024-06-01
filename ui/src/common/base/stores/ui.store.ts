import {API_UNDEFINED, Bundle, SportApi} from "../models/generic.model";
import {makeAutoObservable} from "mobx";
import {RootStore} from "./root.store";
import {
    ApiSportComponentMetadataContainer,
    SportComponentKeys, SportComponentMetadata,
    SportComponentsMetadataModel, SportComponentStatus
} from "../../../api/common/base/models/sport-components.metadata.model";
import {ApiCodeKeys, APIS, getApiCodeKey} from "../../../api/apis";

export class UiStore {
    rootStore: RootStore | null;
    _apiInUse: SportApi = API_UNDEFINED;
    bundle: Bundle = {};
    _apiSportComponentsMetadata: SportComponentsMetadataModel | null = null;
    _apiCodeKeyInUse: ApiCodeKeys = APIS.API_UNDEFINED;

    constructor(rootStore: RootStore | null, apiSportComponentsMetadata?: SportComponentsMetadataModel | null, bundle?: Bundle) {
        makeAutoObservable(this);
        this.bundle = bundle || this.bundle;
        this._apiSportComponentsMetadata = apiSportComponentsMetadata || this._apiSportComponentsMetadata;
        this.rootStore = rootStore;
        this.apiInUse = rootStore?.authStore.api;
    }

    set apiInUse(apiInUse: SportApi | undefined) {
        this._apiInUse = apiInUse || API_UNDEFINED;
        this._apiCodeKeyInUse = getApiCodeKey(apiInUse?.code ?? API_UNDEFINED.code);
    }

    get apiInUse(): SportApi {
        return this._apiInUse;
    }

    get apiSportComponentsMetadata(): SportComponentsMetadataModel | null {
        return this._apiSportComponentsMetadata;
    }

    getApiBasePath() {
        return this._apiInUse.basePath;
    }

    /**
     * Returns an object of specific type T for different api metadata containers
     */
    get thisApiSportComponentMetadataContainer(): <T extends ApiSportComponentMetadataContainer<T>>(containerType: new (...args: any[]) => T) => T | undefined {
        return <T extends ApiSportComponentMetadataContainer<T>>(containerType: new (...args: any[]) => T) => {
            const apiComponentsMetadata = this.apiSportComponentsMetadata?.apiComponentsMetadata[this._apiCodeKeyInUse];
            const instance = apiComponentsMetadata?.getInstance();
            return apiComponentsMetadata && instance instanceof containerType ? instance as T : undefined;
        }
    }

    get thisApiSportComponentHelpText(): (component: SportComponentKeys) => Bundle | undefined {
        return (component: SportComponentKeys) => {
            return this.apiSportComponentHelpText(this._apiCodeKeyInUse, component);
        }
    }

    get apiSportComponentHelpText(): (api: ApiCodeKeys, component: SportComponentKeys | 'commons') => Bundle | undefined {
        return (api: ApiCodeKeys, component: SportComponentKeys | 'commons') => {
            return this.apiSportComponentsMetadata?.apiComponentsMetadata[api]?.helpText[component].bundle;
        }
    }

    get thisApiSportComponentCommonsHelpText(): () => Bundle | undefined {
        return () => {
            return this.apiSportComponentCommonsHelpText(this._apiCodeKeyInUse);
        }
    }

    get apiSportComponentCommonsHelpText(): (api: ApiCodeKeys) => Bundle | undefined {
        return (api: ApiCodeKeys) => {
            return this.apiSportComponentHelpText(api, 'commons');
        }
    }

    get thisApiSportComponentMetadata(): (component: SportComponentKeys) => SportComponentMetadata | undefined {
        return (component: SportComponentKeys) => {
            return this.apiSportComponentMetadata(this._apiCodeKeyInUse, component);
        }
    }

    get apiSportComponentMetadata(): (api: ApiCodeKeys, component: SportComponentKeys) => SportComponentMetadata | undefined {
        return (api: ApiCodeKeys, component: SportComponentKeys) => {
            return this.apiSportComponentsMetadata?.apiComponentsMetadata[api]?.components[component];
        }
    }

    get apiSportComponentMetadataStatus(): (api: ApiCodeKeys, component: SportComponentKeys) => SportComponentStatus {
        return (api: ApiCodeKeys, component: SportComponentKeys) => {
            return this.apiSportComponentMetadata(api, component)?.status || 'DOWN';
        }
    }

    get isApiSportComponentMetadataAccessible(): (api: ApiCodeKeys, component: SportComponentKeys) => boolean {
        return (api: ApiCodeKeys, component: SportComponentKeys) => {
            return this.apiSportComponentMetadataStatus(api, component) === 'ACCESSIBLE';
        }
    }

    get isThisApiSportComponentMetadataAccessible(): (component: SportComponentKeys) => boolean {
        return (component: SportComponentKeys) => {
            return this.isApiSportComponentMetadataAccessible(this._apiCodeKeyInUse, component);
        }
    }
}