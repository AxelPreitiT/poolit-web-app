import QueryError from "./QueryError";

abstract class ResponseError extends QueryError {
  constructor(
    i18nKey: string,
    errorId: string,
    message: string = "ResponseError"
  ) {
    super(i18nKey, errorId, message);
  }

  abstract getStatusText(): string;

  abstract getStatusCode(): number;
}

export default ResponseError;
