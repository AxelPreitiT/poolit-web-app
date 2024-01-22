import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const forbiddenStatusCode = 403;

class ForbiddenResponseError extends ResponseError {
  private static readonly STATUS_CODE: number = forbiddenStatusCode;
  private static readonly STATUS_TEXT: string = "Forbidden";
  private static readonly I18N_KEY: string = "query.response.error.forbidden";
  public static readonly ERROR_ID: string = "ForbiddenResponseError";

  constructor(error: AxiosError) {
    const message = error.message || ForbiddenResponseError.STATUS_TEXT;
    super(
      ForbiddenResponseError.I18N_KEY,
      ForbiddenResponseError.ERROR_ID,
      message
    );
  }

  public getStatusCode(): number {
    return ForbiddenResponseError.STATUS_CODE;
  }

  public getStatusText(): string {
    return ForbiddenResponseError.STATUS_TEXT;
  }
}

ResponseErrorDispatcher.register(forbiddenStatusCode, ForbiddenResponseError);

export default ForbiddenResponseError;
