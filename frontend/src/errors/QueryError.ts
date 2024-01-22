abstract class QueryError extends Error {
  private readonly i18nKey: string;
  private readonly errorId: string = "QueryError";

  constructor(
    i18nKey: string,
    errorId: string,
    message: string = "QueryError"
  ) {
    super(message);
    this.i18nKey = i18nKey;
    this.errorId = errorId;
  }

  public getI18nKey(): string {
    return this.i18nKey;
  }

  public getErrorId(): string {
    return this.errorId;
  }
}

export default QueryError;
