import {useState} from "react";

function useActiveValue(initValue: string | undefined) {
    const [activeValue, setActiveValue] = useState<string | undefined>(initValue);
    const getActiveValue = () => {
        return (activeValue !== undefined ? activeValue : initValue) || '';
    }
    return [getActiveValue, setActiveValue] as const;
}

export function useActiveSeason(initSeason: string | undefined) {
    return useActiveValue(initSeason);
}

export function useActiveStatCategory(initStatCategory: string | undefined) {
    return useActiveValue(initStatCategory);
}