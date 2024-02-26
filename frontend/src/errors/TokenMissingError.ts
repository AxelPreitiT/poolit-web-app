import RequestError from "./RequestError";

class TokenMissingError extends RequestError {
  private static readonly I18N_KEY: string =
    "verify_account.error.token_missing";
  private static readonly ERROR_ID: string = "TokenMissingError";

  constructor() {
    super(
      TokenMissingError.I18N_KEY,
      TokenMissingError.ERROR_ID,
      "token is missing"
    );
  }
}

export default TokenMissingError;
