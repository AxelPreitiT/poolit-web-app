function createPaginationUri(
  baseUri: string,
  currentPage: number,
  pageSize: number,
  first?: boolean
): string {
  const queryParams = new URLSearchParams();
  queryParams.append("page", (currentPage - 1).toString());
  queryParams.append("pageSize", pageSize.toString());
  if (first == true) {
    return `${baseUri}?${queryParams.toString()}`;
  }
  return `${baseUri}&${queryParams.toString()}`;
}

export default createPaginationUri;
