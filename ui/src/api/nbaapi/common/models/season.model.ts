import {UiWrapperResponse} from "../../../../common/base/models/ui.helper.model";

export interface ApiNbaSeasonMap<T> {
    data: ApiNbaDataSeasonMap<T>;
    seasons: ApiNbaSeason[];
}

export interface ApiNbaSeason extends UiWrapperResponse<string> {

}

type ApiNbaDataSeasonMap<T> = Record<string, T>;