import RequestError from "./RequestError";

class AuthorizationHeaderMissingError extends RequestError {
  private static readonly I18N_KEY: string =
    "query.request.error.authorization_header_missing";

  constructor(message: string) {
    super(AuthorizationHeaderMissingError.I18N_KEY, message);
  }
}

export default AuthorizationHeaderMissingError;
