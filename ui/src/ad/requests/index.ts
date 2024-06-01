import {baseAxios} from "common/base/requests/base";
import {SportApi} from "common/base/models/generic.model";

export const getApis = () => baseAxios.get<SportApi[]>('/apis')
    .then(res => res.data as SportApi[]);