export interface UiHint {
    title: string;
    description: string | null;
    className: string | null;
}

export interface UiWrapperResponse<T> {
    value: T;
    uiText: string;
}