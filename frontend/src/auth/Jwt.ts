import { jwtDecode } from "jwt-decode";
import JwtPayload from "./JwtPayload";

class Jwt {
  private static readonly AUTH_TOKEN_KEY: string = "authToken";
  private static readonly REFRESH_TOKEN_KEY: string = "refreshToken";
  private static readonly REMEMBER_ME_KEY: string = "rememberMe";

  private static hasTokenExpired: (token: string) => boolean = (
    token: string
  ) => {
    const claims = jwtDecode<JwtPayload>(token);
    const exp = claims.exp;
    return !!(exp && exp * 1000 < Date.now().valueOf());
  };

  public static getRememberMe: () => boolean = () => {
    const rememberMe: string | null = localStorage.getItem(Jwt.REMEMBER_ME_KEY);
    return rememberMe ? rememberMe === "true" : false;
  };

  private static removeToken: (key: string) => void = (key: string) => {
    const rememberMe: boolean = Jwt.getRememberMe();
    if (!rememberMe) {
      sessionStorage.removeItem(key);
    } else {
      localStorage.removeItem(key);
    }
  };

  private static getToken: (key: string) => `Bearer ${string}` | null = (
    key: string
  ) => {
    const rememberMe: boolean = Jwt.getRememberMe();
    const token = rememberMe
      ? localStorage.getItem(key)
      : sessionStorage.getItem(key);
    if (!token) {
      return null;
    }
    if (Jwt.hasTokenExpired(token)) {
      Jwt.removeToken(key);
      return null;
    }
    return `Bearer ${token}`;
  };

  private static storeToken: (key: string, token: `Bearer ${string}`) => void =
    (key: string, token: `Bearer ${string}`) => {
      const rememberMe: boolean = Jwt.getRememberMe();
      const tokenValue: string = token.split(" ")[1];
      if (!rememberMe) {
        localStorage.removeItem(key);
        sessionStorage.setItem(key, tokenValue);
      } else {
        sessionStorage.removeItem(key);
        localStorage.setItem(key, tokenValue);
      }
    };

  private static removeAuthToken: () => void = () => {
    this.removeToken(Jwt.AUTH_TOKEN_KEY);
  };

  private static removeRefreshToken: () => void = () => {
    this.removeToken(Jwt.REFRESH_TOKEN_KEY);
  };

  public static storeAuthToken: (token: `Bearer ${string}`) => void = (
    token: `Bearer ${string}`
  ) => {
    this.storeToken(Jwt.AUTH_TOKEN_KEY, token);
  };

  public static storeRefreshToken: (token: `Bearer ${string}`) => void = (
    token: `Bearer ${string}`
  ) => {
    this.storeToken(Jwt.REFRESH_TOKEN_KEY, token);
  };

  public static setRememberMe: (rememberMe: boolean) => void = (
    rememberMe: boolean
  ) => {
    localStorage.setItem(Jwt.REMEMBER_ME_KEY, rememberMe.toString());
  };

  public static getAuthToken: () => `Bearer ${string}` | null = () => {
    return this.getToken(Jwt.AUTH_TOKEN_KEY);
  };

  public static getRefreshToken: () => `Bearer ${string}` | null = () => {
    return this.getToken(Jwt.REFRESH_TOKEN_KEY);
  };

  public static removeTokens: () => void = () => {
    Jwt.removeAuthToken();
    Jwt.removeRefreshToken();
  };

  public static getJwtClaims: () => JwtPayload | null = () => {
    const authToken = Jwt.getAuthToken();
    const refreshToken = Jwt.getRefreshToken();
    if (!authToken && !refreshToken) {
      return null;
    }
    const token = ((authToken || refreshToken) as string).split(" ")[1];
    const claims = jwtDecode<JwtPayload>(token);
    return claims;
  };

  public static isAuthenticated: () => boolean = () =>
    Jwt.getJwtClaims() !== null;
}

export default Jwt;
