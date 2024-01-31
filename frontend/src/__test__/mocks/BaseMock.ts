import { HttpResponse, HttpResponseInit, http } from "msw";

class BaseMock {
  protected static readonly OK_STATUS = 200;
  protected static readonly UNAUTHORIZED_STATUS = 401;

  protected static plainResponse(options?: HttpResponseInit) {
    return new HttpResponse(null, options);
  }

  protected static jsonResponse = HttpResponse.json;

  protected static get = http.get;
}

export default BaseMock;
