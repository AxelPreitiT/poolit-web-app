function createStatusPaginationUri(baseUri: string, status: string): string {
  if (status === "ALL") {
    return `${baseUri}`;
  }
  const queryParams = new URLSearchParams();
  queryParams.append("passengerState", status);
  return `${baseUri}&${queryParams.toString()}`;
}

export default createStatusPaginationUri;
