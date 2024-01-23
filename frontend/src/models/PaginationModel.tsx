
interface PaginationModel<T>{
    first: string;
    prev: string;
    next: string;
    last: string;
    data: T[];
}

export default PaginationModel;