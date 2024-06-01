import React from "react";

type Column = {
    header: string;
    accessorKey: string;
    cell: any;
}

type Props = {
    /**
     * Callback to get the header name by object key name
     * @param k object key
     */
    getHeader: (k: string) => string,
    data: Object,
    /**
     * Callback to individually render cell based on the props
     * @param k object key
     * @param v object value
     */
    cellStyle?: (k: string, v: any) => React.ReactElement
}

export const createColumns = (props: Props): Column[] => {
    return Object.entries(props.data).map(([k,v]) => {
        const cellStyle = props.cellStyle;
        return {
            header: props.getHeader(k),
            accessorKey: k,
            cell: (cellStyle && cellStyle(k, v)) || <div>{v}</div>
        }
    });
}