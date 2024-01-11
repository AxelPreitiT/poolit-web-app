import QueryError from "./QueryError";

class AlreadyAuthenticatedError extends QueryError {
  private static readonly I18N_KEY: string =
    "query.response.error.already_authenticated";

  constructor() {
    super(
      AlreadyAuthenticatedError.I18N_KEY,
      "user has already been authenticated"
    );
  }
}

export default AlreadyAuthenticatedError;
