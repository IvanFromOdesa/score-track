import axios from "axios";
import {BASE, getFormDataHeaders} from "common/requests/base";
import {ProfilePageModel} from "../model/profile_page.model";
import {plainToInstance} from "class-transformer";
import {GenericPostServerResponse, Sport} from "common/models/generic.model";
import {FormikValues} from "formik";
import {Profile} from "common/stores/auth.store";

export const initProfilePage = () => axios.get<ProfilePageModel>(`${BASE}/profile/init`)
    .then(res => plainToInstance(ProfilePageModel, res.data as ProfilePageModel));

export const searchSports = (name: string) => axios.get<Sport[]>(`${BASE}/search/sports?q=${name}`)
    .then(res => res.data as Sport[]);

export const updateProfile = (data: FormikValues) => {
    const form = new FormData();
    const profileImg = ((({ profileImg}) => ({ profileImg }))(data)) as any;
    delete data.profileImg;

    /**
     * Send dto as application/json and profile img as multipart
     */
    form.append("data", new Blob([JSON.stringify(data)], { type: "application/json" }));
    form.append("profileImg", profileImg['profileImg']);

    return axios.post<GenericPostServerResponse<Profile>>(`${BASE}/profile/update`, form, getFormDataHeaders())
        .then(res => res.data as GenericPostServerResponse<Profile>);
}