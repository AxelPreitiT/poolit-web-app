function extractPathAfterApi(url: string): string {
  const apiIndex = url.indexOf("/api");

  return url.substring(apiIndex + 4); // +5 para incluir "/api/"
}

export default extractPathAfterApi;
