import React from "react";
import {Pagination} from "react-bootstrap";
import {WithPagination} from "./pagination.model";

/**
 * Sets active page number
 */
type PageSetter = (n: number) => void;

interface IBasePaginationProps<T> {
    activePage: number,
    pageSetter: PageSetter,
    pagination: WithPagination<T>,
    // Component that renders the content
    render: () => React.ReactElement
}

function getPaginationItems(active: number, totalPages: number, pageSetter: PageSetter) {
    const items = [];
    const maxPaginationItems = 4;
    const maxExceeded = totalPages > maxPaginationItems;
    for (let number = 0; number < (maxExceeded ? maxPaginationItems : totalPages); number ++) {
        items.push(
            <Pagination.Item key={number} onClick={() => pageSetter(number)} active={number === active}>
                {number + 1}
            </Pagination.Item>,
        );
    }
    if (maxExceeded) {
        items.push(<Pagination.Ellipsis disabled />);
        items.push(<Pagination.Item active={totalPages === active} onClick={() => pageSetter(totalPages)}>{totalPages}</Pagination.Item>)
    }
    return items;
}

function hideOnCondition(condition: boolean) {
    return condition ? 'd-none' : 'd-block';
}

export function BasePagination<T>(props: IBasePaginationProps<T>): React.JSX.Element {
    const pagination = props.pagination;
    const active = props.activePage;
    const totalPages = pagination.totalPages;
    const hasNext = active + 1 < totalPages;
    const hasPrevious = active !== 0;
    const pageSetter = props.pageSetter;

    return (
        <>
            {props.render()}
            <Pagination className="d-flex justify-content-center mt-4">
                <Pagination.First onClick={() => pageSetter(0)} className={hideOnCondition(pagination.first)}/>
                <Pagination.Prev onClick={() => pageSetter(active - 1)} className={hideOnCondition(!hasPrevious)}/>
                {getPaginationItems(active, totalPages, pageSetter)}
                <Pagination.Next onClick={() => pageSetter(active + 1)} className={hideOnCondition(!hasNext)}/>
                <Pagination.Last onClick={() => pageSetter(totalPages - 1)} className={hideOnCondition(pagination.last)}/>
            </Pagination>
        </>
    )
}