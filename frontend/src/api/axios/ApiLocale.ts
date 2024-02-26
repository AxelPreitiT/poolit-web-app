class ApiLocale {
  private static locale: string | null = null;

  public static getLocale = (): string | null => {
    return this.locale;
  };

  public static setLocale = (locale: string | null): void => {
    this.locale = locale;
  };
}

export default ApiLocale;
