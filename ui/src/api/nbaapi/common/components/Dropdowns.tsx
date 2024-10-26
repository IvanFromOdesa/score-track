import {Dropdown, DropdownButton} from "react-bootstrap";
import {useStoreContext} from "common/base/stores/store.context";
import React from "react";
import {UiWrapperResponse} from "common/base/models/ui.helper.model";
import {ApiNbaSeason} from "../models/season.model";
import {ApiNbaStatCategory} from "../models/stat.category.model";

interface DropdownProps<T extends UiWrapperResponse<string>> {
    items: T[] | undefined,
    activeItem: string | undefined,
    setActiveItem: (s: string | undefined) => void,
    titleKey: string,
    selectTitleKey: string,
    getItemValue: (item: T) => string,
    getItemText: (item: T) => string,
    itemKeyPrefix: string
}

function GenericDropdown<T extends UiWrapperResponse<string>>({ items, activeItem, setActiveItem, titleKey, selectTitleKey, getItemValue, getItemText, itemKeyPrefix }: DropdownProps<T>) {
    const { rootStore: { uiStore: { thisApiSportComponentCommonsHelpText } } } = useStoreContext();
    const helpText = thisApiSportComponentCommonsHelpText();

    return (
        <DropdownButton flip title={helpText?.[titleKey]}>
            <Dropdown.ItemText><b>{helpText?.[selectTitleKey]}</b></Dropdown.ItemText>
            {
                items?.map((item, idx) => {
                    const value = getItemValue(item);
                    return (
                        <Dropdown.Item
                            key={`${itemKeyPrefix}-${idx}`}
                            as="button"
                            onClick={() => setActiveItem(value)}
                            active={value === activeItem}
                        >
                            {getItemText(item)}
                        </Dropdown.Item>
                    )
                })
            }
        </DropdownButton>
    );
}

interface DropdownStatCategoriesProps {
    statCategories: ApiNbaStatCategory[] | undefined,
    activeStatCategory: string | undefined,
    setActiveStatCategory: (s: string | undefined) => void
}

const DropdownStatCategories: React.FC<DropdownStatCategoriesProps> = ({ statCategories, activeStatCategory, setActiveStatCategory }) => (
    <GenericDropdown
        items={statCategories}
        activeItem={activeStatCategory}
        setActiveItem={setActiveStatCategory}
        titleKey="statCategoryTitle"
        selectTitleKey="selectStatCategoryTitle"
        getItemValue={category => category.value}
        getItemText={category => category.uiText}
        itemKeyPrefix="nbaapi-stat-category-dropdown"
    />
);

interface DropdownSeasonsProps {
    seasons: ApiNbaSeason[] | undefined,
    activeSeason: string | undefined,
    setActiveSeason: (s: string | undefined) => void
}

const DropdownSeasons: React.FC<DropdownSeasonsProps> = ({ seasons, activeSeason, setActiveSeason }) => (
    <GenericDropdown
        items={seasons}
        activeItem={activeSeason}
        setActiveItem={setActiveSeason}
        titleKey="seasonsTitle"
        selectTitleKey="selectSeasonTitle"
        getItemValue={season => season.value}
        getItemText={season => season.uiText}
        itemKeyPrefix="nbaapi-season-dropdown"
    />
);

export { DropdownStatCategories, DropdownSeasons };