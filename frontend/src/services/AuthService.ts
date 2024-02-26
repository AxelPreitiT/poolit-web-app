import AuthApi from "@/api/AuthApi";
import Service from "./Service";

class AuthService extends Service {
  public static isAuthenticated = () => AuthApi.isAuthenticated();
}

export default AuthService;
