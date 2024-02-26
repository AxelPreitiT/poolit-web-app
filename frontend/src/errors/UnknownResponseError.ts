import ResponseError from "./ResponseError";

const unknownStatusCode = -1;

class UnknownResponseError extends ResponseError {
  private static readonly STATUS_CODE: number = unknownStatusCode;
  private static readonly STATUS_TEXT: string = "Unknown";
  private static readonly I18N_KEY: string = "query.response.error.unknown";
  public static readonly ERROR_ID: string = "UnknownResponseError";

  constructor(error?: Error) {
    const message = error?.message || UnknownResponseError.STATUS_TEXT;
    super(
      UnknownResponseError.I18N_KEY,
      UnknownResponseError.ERROR_ID,
      message
    );
  }

  public getStatusCode(): number {
    return UnknownResponseError.STATUS_CODE;
  }

  public getStatusText(): string {
    return UnknownResponseError.STATUS_TEXT;
  }
}

export default UnknownResponseError;
