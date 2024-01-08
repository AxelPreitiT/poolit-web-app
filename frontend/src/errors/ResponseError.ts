import QueryError from "./QueryError";

abstract class ResponseError extends QueryError {
  constructor(i18nKey: string, message: string = "ResponseError") {
    super(i18nKey, message);
  }

  abstract getStatusText(): string;

  abstract getStatusCode(): number;
}

export default ResponseError;
