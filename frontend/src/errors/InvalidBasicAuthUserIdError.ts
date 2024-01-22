import RequestError from "./RequestError";

class InvalidBasicAuthUserIdError extends RequestError {
  private static readonly I18N_KEY: string =
    "query.request.error.invalid_email";
  private static readonly ERROR_ID: string = "InvalidBasicAuthUserIdError";

  constructor(message: string) {
    super(
      InvalidBasicAuthUserIdError.I18N_KEY,
      InvalidBasicAuthUserIdError.ERROR_ID,
      message
    );
  }
}

export default InvalidBasicAuthUserIdError;
