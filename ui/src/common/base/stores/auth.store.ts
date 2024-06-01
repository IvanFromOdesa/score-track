import {API_UNDEFINED, CodeName, Sport, SportApi} from "../models/generic.model";
import {makeAutoObservable} from "mobx";
import {getDate, getDateTime} from "../utils/date";
import {ACCESS_TOKEN, RECAPTCHA_KEY} from "../utils/callback.injector";

export class AuthStore {
    userData: UserData = getDefaultUserData();
    // TODO: refactor into separate interface (not only profile may be updatable)
    _profileUpdated: boolean = false;

    constructor(userData?: UserData) {
        makeAutoObservable(this);
        this.userData = userData || this.userData;
        ACCESS_TOKEN.callback = () => this.token;
        RECAPTCHA_KEY.callback = () => this.recaptchaKey;
    }

    get lastSeenDate(): string {
        return getDateTime(this.userData.lastSeen, this.userData.locale);
    }

    get api(): SportApi {
        return this.availableApis[0] || API_UNDEFINED;
    }

    get isApiCodeAvailable(): (code: number) => boolean {
        return (code: number) => {
            return this.availableApis.some(a => a.code === code);
        }
    }

    get availableApis(): SportApi[] {
        const plan = this.userData.viewershipPlan;
        return (this.authenticated && plan?.active) ? (plan?.plannedViewership?.sportApis || (plan?.customAvailableApis ?? [])) : [];
    }

    get authenticated(): boolean {
        return Boolean(this.token.value).valueOf();
    }

    get token(): AccessToken {
        return this.userData.token || ACCESS_TOKEN.default;
    }

    get recaptchaKey(): string {
        return this.userData.recaptchaKey;
    }

    set profile(profile: Profile) {
        this.userData.profile = profile;
        this._profileUpdated = true;
    }

    set profileUpdated(profileUpdated: boolean) {
        this._profileUpdated = profileUpdated;
    }

    get profileUpdated(): boolean {
        return this._profileUpdated;
    }
}

export class UserData implements IUserData {
    lastSeen: number | null;
    locale: string;
    profile: Profile | null;
    token: AccessToken | null;
    recaptchaKey: string;
    viewershipPlan: ViewershipPlan | null;

    constructor(lastSeen: number | null, locale: string, profile: Profile | null, token: AccessToken | null, recaptchaKey: string, viewershipPlan: ViewershipPlan | null) {
        this.lastSeen = lastSeen;
        this.locale = locale;
        this.profile = profile;
        this.token = token;
        this.recaptchaKey = recaptchaKey;
        this.viewershipPlan = viewershipPlan;
    }

    getProfileUrl(): string {
        return this.profile?.profileImg?.url || ''
    }

    getFullName(): string {
        const firstName = this.profile?.firstName;
        const lastName = this.profile?.lastName;
        const present = firstName && lastName;
        return (firstName || '') + (present ? ' ' : '') + (lastName || '');
    }

    getViewershipPlanEndDateTime(full: boolean): string {
        const timestamp = this.viewershipPlan?.endDateTime;
        return full ? getDateTime(timestamp, this.locale) : getDate(timestamp, this.locale);
    }

    getProfile(): Profile {
        return this.profile || getDefaultProfile();
    }
}

export interface IUserData {
    token: AccessToken | null;
    recaptchaKey: string;
    profile: Profile | null;
    viewershipPlan: ViewershipPlan | null;
    lastSeen: number | null;
    locale: string;

    getProfileUrl(): string;
    getFullName(): string;
    getViewershipPlanEndDateTime(full: boolean): string;
    getProfile(): Profile;
}

export interface AccessToken {
    value: string;
    exp: number;
}

export interface Profile extends CommonProfile {
    /**
     * Nickname cannot be undefined. Default is the same as loginname.
     */
    nickname: string;
    profileImg?: ProfileImg;
}

export interface CommonProfile {
    instagramLink?: string;
    xLink?: string;
    firstName?: string;
    lastName?: string;
    dob?: string;
    bio?: string;
    sportPreference?: Sport[];
}

export interface ProfileImg {
    url: string | null;
    accessStatus: ImgAccessStatus;
    prompt: string | null;
}

export interface ImgAccessStatus {
    code: number;
    requiresReview: boolean;
    accessible: boolean;
    nsfw: boolean;
    undefined: boolean;
}

export interface ViewershipPlan {
    active: boolean;
    endDateTime: number;
    customAvailableApis: SportApi[] | null;
    plannedViewership: PlannedViewership | null;
}

export interface PlannedViewership extends CodeName {
    sportApis: SportApi[];
}

const getDefaultProfile = (): Profile => {
    return {
        nickname: '',
    };
}

export const getDefaultCommonProfile = (): CommonProfile => {
    return {
        instagramLink: "",
        xLink: "",
        firstName: "",
        lastName: "",
        dob: "",
        bio: "",
        sportPreference: []
    }
}

const getDefaultUserData = (): UserData => {
    return {
        lastSeen: null,
        locale: "en-US", // default locale
        profile: null,
        token: null,
        recaptchaKey: "",
        viewershipPlan: null,
        getProfileUrl(): string {
            return "";
        },
        getFullName(): string {
            return "";
        },
        getViewershipPlanEndDateTime(full: boolean): string {
            return "";
        },
        getProfile(): Profile {
            return getDefaultProfile();
        }
    };
}