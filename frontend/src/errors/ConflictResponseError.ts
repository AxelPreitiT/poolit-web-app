import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";

const conflictStatusCode = 409;

class ConflictResponseError extends ResponseError {
  private static readonly STATUS_CODE: number = conflictStatusCode;
  private static readonly STATUS_TEXT: string = "Conflict";
  private static readonly I18N_KEY: string = "query.response.error.conflict";
  public static readonly ERROR_ID: string = "ConflictResponseError";

  constructor(error: AxiosError) {
    const message = error.message || ConflictResponseError.STATUS_TEXT;
    super(
      ConflictResponseError.I18N_KEY,
      ConflictResponseError.ERROR_ID,
      message
    );
  }

  public getStatusCode(): number {
    return ConflictResponseError.STATUS_CODE;
  }

  public getStatusText(): string {
    return ConflictResponseError.STATUS_TEXT;
  }
}

ResponseErrorDispatcher.register(conflictStatusCode, ConflictResponseError);

export default ConflictResponseError;
