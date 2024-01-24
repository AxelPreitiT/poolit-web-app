function CreatePaginationUri(baseUri: string , currentPage: number , pageSize: number): string | null {

  const queryParams = new URLSearchParams();
  queryParams.append('page', (currentPage-1).toString());
  queryParams.append('pageSize', pageSize.toString());
  return `${baseUri}&${queryParams.toString()}`;
}

export default CreatePaginationUri;