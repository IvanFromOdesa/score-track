import {Bundle} from "common/models/generic.model";

export class ProfilePageModel {
    maxUploadFileSize: string;
    bundle: Bundle;

    constructor(bundle: Bundle, maxUploadFileSize: string) {
        this.bundle = bundle;
        this.maxUploadFileSize = maxUploadFileSize;
    }
}

export const _DEFAULT = new ProfilePageModel({}, '');