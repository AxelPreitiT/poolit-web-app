import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const forbiddenStatusCode = 403;
const forbiddenStatusText = "Forbidden";
const forbiddenI18nKey = "errors.response.forbidden";

class ForbiddenResponseError extends ResponseError {
  constructor(responseMessage?: string) {
    super(forbiddenI18nKey, responseMessage);
  }

  public getStatusCode(): number {
    return forbiddenStatusCode;
  }

  public getStatusText(): string {
    return forbiddenStatusText;
  }
}

ResponseErrorDispatcher.register(forbiddenStatusCode, ForbiddenResponseError);
