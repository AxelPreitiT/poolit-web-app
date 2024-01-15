import RequestError from "./RequestError";

class EmailMissingError extends RequestError {
  private static readonly I18N_KEY: string =
    "verify_account.error.email_missing";
  private static readonly ERROR_ID: string = "EmailMissingError";

  constructor() {
    super(
      EmailMissingError.I18N_KEY,
      EmailMissingError.ERROR_ID,
      "email is missing"
    );
  }
}

export default EmailMissingError;
