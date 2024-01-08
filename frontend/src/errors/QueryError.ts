abstract class QueryError extends Error {
  public readonly i18nKey: string;

  constructor(i18nKey: string, message: string = "QueryError") {
    super(message);
    this.i18nKey = i18nKey;
  }

  public getI18nKey(): string {
    return this.i18nKey;
  }
}

export default QueryError;
