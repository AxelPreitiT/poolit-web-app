class Jwt {
  private static readonly AUTH_TOKEN_KEY: string = "authToken";
  private static readonly REFRESH_TOKEN_KEY: string = "refreshToken";
  private static readonly REMEMBER_ME_KEY: string = "rememberMe";

  public static storeAuthToken: (token: `Bearer ${string}`) => void = (
    token: `Bearer ${string}`
  ) => {
    const authToken: string = token.split(" ")[1];
    const rememberMe: boolean = Jwt.getRememberMe();
    if (!rememberMe) {
      localStorage.removeItem(Jwt.AUTH_TOKEN_KEY);
      sessionStorage.setItem(Jwt.AUTH_TOKEN_KEY, authToken);
    } else {
      sessionStorage.removeItem(Jwt.AUTH_TOKEN_KEY);
      localStorage.setItem(Jwt.AUTH_TOKEN_KEY, authToken);
    }
  };

  public static storeRefreshToken: (token: `Bearer ${string}`) => void = (
    token: `Bearer ${string}`
  ) => {
    const refreshToken: string = token.split(" ")[1];
    const rememberMe: boolean = Jwt.getRememberMe();
    if (!rememberMe) {
      localStorage.removeItem(Jwt.REFRESH_TOKEN_KEY);
      sessionStorage.setItem(Jwt.REFRESH_TOKEN_KEY, refreshToken);
    } else {
      sessionStorage.removeItem(Jwt.REFRESH_TOKEN_KEY);
      localStorage.setItem(Jwt.REFRESH_TOKEN_KEY, refreshToken);
    }
  };

  public static setRememberMe: (rememberMe: boolean) => void = (
    rememberMe: boolean
  ) => {
    localStorage.setItem(Jwt.REMEMBER_ME_KEY, rememberMe.toString());
  };

  public static getAuthToken: () => `Bearer ${string}` | null = () => {
    const rememberMe: boolean = Jwt.getRememberMe();
    const authToken: string | null = rememberMe
      ? localStorage.getItem(Jwt.AUTH_TOKEN_KEY)
      : sessionStorage.getItem(Jwt.AUTH_TOKEN_KEY);
    return authToken ? `Bearer ${authToken}` : null;
  };

  public static getRefreshToken: () => `Bearer ${string}` | null = () => {
    const rememberMe: boolean = Jwt.getRememberMe();
    const refreshToken: string | null = rememberMe
      ? localStorage.getItem(Jwt.REFRESH_TOKEN_KEY)
      : sessionStorage.getItem(Jwt.REFRESH_TOKEN_KEY);
    return refreshToken ? `Bearer ${refreshToken}` : null;
  };

  public static getRememberMe: () => boolean = () => {
    const rememberMe: string | null = localStorage.getItem(Jwt.REMEMBER_ME_KEY);
    return rememberMe ? rememberMe === "true" : false;
  };
}

export default Jwt;
