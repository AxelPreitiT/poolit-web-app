import { HttpResponse, HttpResponseInit, http } from "msw";

class BaseMock {
  protected static readonly OK_STATUS = 200;
  protected static readonly NO_CONTENT_STATUS = 204;
  protected static readonly UNAUTHORIZED_STATUS = 401;
  private static readonly BASE_URL = "http://localhost:8080/paw-2023a-07/api";

  protected static getPath(path: string): string {
    return `${this.BASE_URL}${path}`
  }

  protected static plainResponse(options?: HttpResponseInit) {
    return new HttpResponse(null, options);
  }

  protected static jsonResponse = HttpResponse.json;

  protected static get = http.get;

  protected static options = http.options;

}

export default BaseMock;
