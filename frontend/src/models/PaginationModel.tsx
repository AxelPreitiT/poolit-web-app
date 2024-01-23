
interface PaginationModel<T>{
    first: string;
    prev: string;
    next: string;
    last: string;
    total_pages: number;
    data: T[];
}

export default PaginationModel;