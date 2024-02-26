interface PaginationModel<T> {
  first: string;
  prev: string;
  next: string;
  last: string;
  totalPages: number;
  data: T[];
}

export default PaginationModel;
