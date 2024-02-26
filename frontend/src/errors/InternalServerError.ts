import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const internalServerErrorStatusCode = 500;

class InternalServerError extends ResponseError {
  private static readonly STATUS_CODE: number = internalServerErrorStatusCode;
  private static readonly STATUS_TEXT: string = "Internal Server Error";
  private static readonly I18N_KEY: string =
    "query.response.error.internal_server_error";
  public static readonly ERROR_ID: string = "InternalServerError";

  constructor(error: AxiosError) {
    const message = error.message || InternalServerError.STATUS_TEXT;
    super(InternalServerError.I18N_KEY, InternalServerError.ERROR_ID, message);
  }

  public getStatusCode(): number {
    return InternalServerError.STATUS_CODE;
  }

  public getStatusText(): string {
    return InternalServerError.STATUS_TEXT;
  }
}

ResponseErrorDispatcher.register(
  internalServerErrorStatusCode,
  InternalServerError
);

export default InternalServerError;
