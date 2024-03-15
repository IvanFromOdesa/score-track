export interface CodeName {
    name: string,
    code: number
}

export interface Sport extends CodeName {

}

export interface SportApi extends CodeName {
    basePath: string;
    sportTypes: number[];
    logoUrl: string;
}

export const API_UNDEFINED: SportApi = {
    code: -1,
    name: "",
    sportTypes: [],
    basePath: "",
    logoUrl: ""
};

export interface GenericComponentProps {
    bundle: Bundle | undefined;
}

export interface WithLoadingProps extends GenericComponentProps {
    loading: boolean;
}

export type Bundle = {
    [name: string]: string;
}

export interface GenericPostServerResponse<T> {
    errors?: ReadonlyProps<ErrorMap>;
    data?: T;
}

/**
 * Server validation errors.
 */
export type ErrorMap = {
    [name: string]: Error;
}

export type Error = {
    msg?: string;
    errors?: Record<string, string>;
}

export type ReadonlyProps<T> = {
    readonly [P in keyof T]: T[P];
}