export type MergeParams = {[s: string]: unknown};

export const merge = (from: MergeParams, to: MergeParams) => {
    Object.entries(from).forEach(([key, name]) => {
        if (!to.hasOwnProperty(key)) {
            to[key] = from[key];
        }
    });
    return to;
}