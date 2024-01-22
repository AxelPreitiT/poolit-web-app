import { AxiosPromise } from "axios";
import AxiosApi from "./axios/AxiosApi";
import UsersApi from "./UsersApi";
import DiscoveryApi from "./DiscoveryApi";

class AuthApi extends AxiosApi {
  public static authenticate: (
    Authorization: `Basic ${string}`
  ) => AxiosPromise = (Authorization: `Basic ${string}`) =>
    DiscoveryApi.getDiscovery({
      headers: {
        Authorization,
      },
    });

  public static tryAuthentication: () => AxiosPromise = () =>
    UsersApi.getCurrentUser();
}

export default AuthApi;
