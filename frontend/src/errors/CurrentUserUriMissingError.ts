import RequestError from "./RequestError";

class CurrentUserUriMissingError extends RequestError {
  private static readonly I18N_KEY: string =
    "query.request.error.current_user_uri_missing";
  public static readonly ERROR_ID: string = "CurrentUserUriMissingError";

  constructor(message: string = "MissingCurrentUserUriError") {
    super(
      CurrentUserUriMissingError.I18N_KEY,
      CurrentUserUriMissingError.ERROR_ID,
      message
    );
  }
}

export default CurrentUserUriMissingError;
