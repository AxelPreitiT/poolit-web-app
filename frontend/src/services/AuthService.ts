import AuthApi from "@/api/AuthApi";
import Service from "./Service";

class AuthService extends Service {
  public static tryAuthentication = async () => {
    await this.resolveQuery(AuthApi.tryAuthentication());
  };
}

export default AuthService;
