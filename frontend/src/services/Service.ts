import ErrorModel from "@/models/ErrorModel";
import { AxiosResponse } from "axios";

const OkHttpStatusCode = 200;

abstract class Service {
  protected static resolveResponse<Model>(
    response: AxiosResponse<Model>
  ): Model {
    if (response.status === OkHttpStatusCode) {
      return response.data;
    }
    throw new Error((response.data as ErrorModel)?.message);
  }
}

export default Service;
