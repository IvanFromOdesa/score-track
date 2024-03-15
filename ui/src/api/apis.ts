export const APIS = {
    API_NBA: 0,
} as const;

export type APIS_T = typeof APIS[keyof typeof APIS];