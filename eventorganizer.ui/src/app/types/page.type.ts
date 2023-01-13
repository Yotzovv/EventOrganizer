export class Page<T> {
    content: Array<T>;
    empty: boolean;
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    pageable: {
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        sort: PageSort,
        unpaged: boolean;
    };
    sort: PageSort;
    totalElements: number;
    totalPages: number; 
}

export class PageSort {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
}