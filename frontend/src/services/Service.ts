import RequestError from "@/errors/RequestError";
import ResponseErrorDispatcher from "@/errors/ResponseErrorDispatcher";
import UnknownResponseError from "@/errors/UnknownResponseError";
import ErrorModel from "@/models/ErrorModel";
import { AxiosError, AxiosPromise } from "axios";

abstract class Service {
  protected static resolveQuery = async <Model = void>(
    query: AxiosPromise<Model>
  ): Promise<Model> => {
    try {
      const response = await query;
      return response.data;
    } catch (error) {
      if (error instanceof RequestError) {
        throw error;
      }
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
  };
}

export default Service;
