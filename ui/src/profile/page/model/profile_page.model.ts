import {Bundle} from "common/models/generic.model";

export class ProfilePageModel {
    bundle: Bundle;

    constructor(bundle: Bundle) {
        this.bundle = bundle;
    }
}

export const _DEFAULT = new ProfilePageModel({});