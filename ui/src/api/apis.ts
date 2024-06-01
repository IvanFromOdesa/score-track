export const APIS = {
    API_NBA: 0,
    API_UNDEFINED: '-1'
} as const;

export type ApiCodeKeys = typeof APIS[keyof typeof APIS];

export function getApiCodeKey(apiCode: number): ApiCodeKeys {
    switch (apiCode) {
        case 0:
            return APIS.API_NBA;
        default:
            return APIS.API_UNDEFINED;
    }
}