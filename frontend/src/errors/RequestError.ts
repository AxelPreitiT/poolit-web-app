import QueryError from "./QueryError";

abstract class RequestError extends QueryError {
  constructor(i18nKey: string, message: string = "RequestError") {
    super(i18nKey, message);
  }
}

export default RequestError;
