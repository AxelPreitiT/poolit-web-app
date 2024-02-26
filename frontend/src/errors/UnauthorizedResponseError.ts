import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const unauthorizedStatusCode = 401;

class UnauthorizedResponseError extends ResponseError {
  private static readonly STATUS_CODE: number = unauthorizedStatusCode;
  private static readonly STATUS_TEXT: string = "Unauthorized";
  private static readonly I18N_KEY: string =
    "query.response.error.unauthorized";
  public static readonly ERROR_ID: string = "UnauthorizedResponseError";

  constructor(error: AxiosError) {
    const message = error.message || UnauthorizedResponseError.STATUS_TEXT;
    super(
      UnauthorizedResponseError.I18N_KEY,
      UnauthorizedResponseError.ERROR_ID,
      message
    );
  }

  public getStatusCode(): number {
    return UnauthorizedResponseError.STATUS_CODE;
  }

  public getStatusText(): string {
    return UnauthorizedResponseError.STATUS_TEXT;
  }
}

ResponseErrorDispatcher.register(
  unauthorizedStatusCode,
  UnauthorizedResponseError
);

export default UnauthorizedResponseError;
