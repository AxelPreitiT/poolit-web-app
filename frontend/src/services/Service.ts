import ResponseErrorDispatcher from "@/errors/ResponseErrorDispatcher";
import UnknownResponseError from "@/errors/UnknownResponseError";
import ErrorModel from "@/models/ErrorModel";
import { AxiosError, AxiosPromise } from "axios";

const OkHttpStatusCode = 200;

abstract class Service {
  protected static async resolveQuery<Model = void>(
    query: AxiosPromise<Model>
  ): AxiosPromise<Model> {
    try {
      const response = await query;
      if (response.status === OkHttpStatusCode) {
        return response;
      }
      throw ResponseErrorDispatcher.dispatch(
        response.status,
        (response.data as ErrorModel)?.message
      );
    } catch (error) {
      if (error instanceof AxiosError && error.response) {
        throw ResponseErrorDispatcher.dispatch(
          error.response.status,
          (error.response.data as ErrorModel)?.message
        );
      }
      const unknownResponseError = new UnknownResponseError();
      throw ResponseErrorDispatcher.dispatch(
        unknownResponseError.getStatusCode()
      );
    }
  }
}

export default Service;
