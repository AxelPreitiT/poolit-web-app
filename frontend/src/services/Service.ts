import ResponseErrorDispatcher from "@/errors/ResponseErrorDispatcher";
import ErrorModel from "@/models/ErrorModel";
import { AxiosPromise } from "axios";

const OkHttpStatusCode = 200;

abstract class Service {
  protected static async resolveQuery<Model = void>(
    query: AxiosPromise<Model>
  ): AxiosPromise<Model> {
    const response = await query;
    if (response.status === OkHttpStatusCode) {
      return response;
    }
    throw ResponseErrorDispatcher.dispatch(
      response.status,
      (response.data as ErrorModel)?.message
    );
  }
}

export default Service;
