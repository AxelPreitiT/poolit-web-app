import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const notFoundStatus = 404;

class NotFoundResponseError extends ResponseError {
  private static readonly STATUS_CODE: number = notFoundStatus;
  private static readonly STATUS_TEXT: string = "Not Found";
  private static readonly I18N_KEY: string = "query.response.error.not_found";
  public static readonly ERROR_ID: string = "NotFoundResponseError";

  constructor(error: AxiosError) {
    const message = error.message || NotFoundResponseError.STATUS_TEXT;
    super(
      NotFoundResponseError.I18N_KEY,
      NotFoundResponseError.ERROR_ID,
      message
    );
  }

  public getStatusCode(): number {
    return NotFoundResponseError.STATUS_CODE;
  }

  public getStatusText(): string {
    return NotFoundResponseError.STATUS_TEXT;
  }
}

ResponseErrorDispatcher.register(notFoundStatus, NotFoundResponseError);

export default NotFoundResponseError;
