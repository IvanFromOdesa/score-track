import {baseAxios, getFormDataHeaders} from "common/base/requests/base";
import {ProfilePageModel} from "../model/profile_page.model";
import {plainToInstance} from "class-transformer";
import {GenericPostServerResponse, Sport} from "common/base/models/generic.model";
import {FormikValues} from "formik";
import {Profile} from "common/base/stores/auth.store";

export const initProfilePage = () => baseAxios.get<ProfilePageModel>('/profile/init')
    .then(res => plainToInstance(ProfilePageModel, res.data as ProfilePageModel));

export const searchSports = (name: string) => baseAxios.get<Sport[]>(`/search/sports?q=${name}`)
    .then(res => res.data as Sport[]);

export const updateProfile = async (data: FormikValues) => {
    const form = new FormData();
    const profileImg = ((({profileImg}) => ({profileImg}))(data)) as any;
    delete data.profileImg;

    /**
     * Send dto as application/json and profile img as multipart
     */
    form.append("data", new Blob([JSON.stringify(data)], {type: "application/json"}));
    form.append("profileImg", profileImg['profileImg']);

    const res = await baseAxios.post<GenericPostServerResponse<Profile>>('/profile/update', form, getFormDataHeaders());
    return res.data as GenericPostServerResponse<Profile>;
}