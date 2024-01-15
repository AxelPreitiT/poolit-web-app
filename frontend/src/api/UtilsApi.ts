import { AxiosPromise } from "axios";
import CitiesApi from "./CitiesApi";
import AxiosApi from "./axios/AxiosApi";
import UsersApi from "./UsersApi";

class UtilsApi extends AxiosApi {
  public static authenticate: (
    Authorization: `Basic ${string}`
  ) => AxiosPromise = (Authorization: `Basic ${string}`) =>
    // Todo: Replace with CitiesApi.getOptions when deployed
    CitiesApi.getAllCities({
      headers: {
        Authorization,
      },
    });

  public static tryAuthentication: () => AxiosPromise = () =>
    UsersApi.getCurrentUser();
}

export default UtilsApi;
