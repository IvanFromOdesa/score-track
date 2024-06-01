export function roundString(value: string) {
    return Math.round((parseFloat(value) + Number.EPSILON) * 100) / 100;
}