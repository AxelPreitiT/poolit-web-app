import RequestError from "./RequestError";

class InvalidBasicAuthUserIdError extends RequestError {
  private static readonly I18N_KEY: string =
    "query.request.error.invalid_email";

  constructor(message: string) {
    super(InvalidBasicAuthUserIdError.I18N_KEY, message);
  }
}

export default InvalidBasicAuthUserIdError;
