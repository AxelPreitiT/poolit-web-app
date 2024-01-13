import QueryError from "./QueryError";

class AccountNotVerifiedError extends QueryError {
  private static readonly I18N_KEY: string = "login.error.account_not_verified";

  constructor(message: string = "AccountNotVerifiedError") {
    super(AccountNotVerifiedError.I18N_KEY, message);
  }
}

export default AccountNotVerifiedError;
