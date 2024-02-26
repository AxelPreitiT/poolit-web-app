import QueryError from "./QueryError";

class AccountNotVerifiedError extends QueryError {
  private static readonly I18N_KEY: string = "login.error.account_not_verified";
  public static readonly ERROR_ID: string = "AccountNotVerifiedError";

  constructor(message: string = "AccountNotVerifiedError") {
    super(
      AccountNotVerifiedError.I18N_KEY,
      AccountNotVerifiedError.ERROR_ID,
      message
    );
  }
}

export default AccountNotVerifiedError;
