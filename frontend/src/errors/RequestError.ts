import QueryError from "./QueryError";

abstract class RequestError extends QueryError {
  constructor(
    i18nKey: string,
    errorId: string,
    message: string = "RequestError"
  ) {
    super(i18nKey, errorId, message);
  }
}

export default RequestError;
