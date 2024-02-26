import QueryError from "./QueryError";

class AlreadyAuthenticatedError extends QueryError {
  private static readonly I18N_KEY: string =
    "query.response.error.already_authenticated";
  public static readonly ERROR_ID: string = "AlreadyAuthenticatedError";

  constructor() {
    super(
      AlreadyAuthenticatedError.I18N_KEY,
      AlreadyAuthenticatedError.ERROR_ID,
      "user has already been authenticated"
    );
  }
}

export default AlreadyAuthenticatedError;
