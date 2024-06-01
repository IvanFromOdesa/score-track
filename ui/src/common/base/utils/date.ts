export const getDate = (timestamp: number | null | undefined, locale: string) => {
    return timestamp ? new Date(timestamp * 1000).toLocaleDateString(locale) : "";
}

export const getDateTime = (timestamp: number | null | undefined, locale: string) => {
    if (timestamp) {
        const date = new Date(timestamp * 1000);
        return date.toLocaleDateString(locale) + " " + formatTime(date.getHours()) + ":" + formatTime(date.getMinutes()) + ":" + formatTime(date.getSeconds());
    } else {
        return "";
    }
}

function formatTime(time: number) {
    return (time < 10 ? '0' : '') + time;
}

export const formatDateString = (timestamp: number | undefined | null) => {
    if (timestamp) {
        const date = new Date(timestamp * 1000);
        return `${date.getFullYear()}-${formatTime(date.getMonth())}-${formatTime(date.getDay())}`;
    }
    return '';
}

export const isBeforeNow = (timestamp: number) => {
    // Server timestamp is in seconds
    return timestamp * 1000 <= new Date().getTime();
}

/**
 * Compares input date with current one in UTC format.
 * @param date string representation of date YYYY-MM-DD
 */
export const isBeforeNowString = (date: string) => new Date(date) <= new Date();
