import QueryError from "@/errors/QueryError";
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
      if (error instanceof QueryError) {
        throw error;
      }
      if (error instanceof AxiosError && error.response) {
        throw ResponseErrorDispatcher.dispatch(
          error.response.status,
          (error.response.data as ErrorModel)?.message
        );
      }
      throw new UnknownResponseError();
    }
  };
}

export default Service;
