import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const unauthorizedStatusCode = 401;
const unauthorizedStatusText = "Unauthorized";
const unauthorizedI18nKey = "errors.response.unauthorized";

class UnauthorizedResponseError extends ResponseError {
  constructor(responseMessage?: string) {
    super(unauthorizedI18nKey, responseMessage);
  }

  public getStatusCode(): number {
    return unauthorizedStatusCode;
  }

  public getStatusText(): string {
    return unauthorizedStatusText;
  }
}

ResponseErrorDispatcher.register(
  unauthorizedStatusCode,
  UnauthorizedResponseError
);
