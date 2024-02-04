import { HttpResponse, HttpResponseInit, http, HttpRequestHandler } from "msw";

class BaseMock {
  protected static readonly OK_STATUS = 200;
  protected static readonly NO_CONTENT_STATUS = 204;
  protected static readonly UNAUTHORIZED_STATUS = 401;
  protected static readonly CREATED_STATUS = 201;
  protected static readonly CONFLICT_STATUS = 409;
  private static readonly BASE_URL = "http://localhost:8080/paw-2023a-07/api";

  protected static getPath(path: string): string {
    return `${this.BASE_URL}${path}`;
  }

  protected static plainResponse(options?: HttpResponseInit) {
    return new HttpResponse(null, options);
  }

  protected static jsonResponse = HttpResponse.json;

  protected static getWithoutBase: HttpRequestHandler = (
    path,
    resolver,
    options
  ) => http.get(path as string, resolver, options);

  protected static get: HttpRequestHandler = (path, resolver, options) =>
    this.getWithoutBase(this.getPath(path as string), resolver, options);

  protected static options: HttpRequestHandler = (path, resolver, options) =>
    http.options(this.getPath(path as string), resolver, options);

  protected static post: HttpRequestHandler = (path, resolver, options) =>
    http.post(this.getPath(path as string), resolver, options);
}

export default BaseMock;
