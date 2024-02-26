import { AxiosPromise } from "axios";
import AxiosApi from "./axios/AxiosApi";
import DiscoveryApi from "./DiscoveryApi";
import Jwt from "@/auth/Jwt";

class AuthApi extends AxiosApi {
  public static authenticate: (
    Authorization: `Basic ${string}`
  ) => AxiosPromise = (Authorization: `Basic ${string}`) =>
    DiscoveryApi.getDiscovery({
      headers: {
        Authorization,
      },
    });

  public static isAuthenticated: () => boolean = () => Jwt.isAuthenticated();
}

export default AuthApi;
