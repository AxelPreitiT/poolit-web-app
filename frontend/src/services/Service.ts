import QueryError from "@/errors/QueryError";
import ResponseErrorDispatcher from "@/errors/ResponseErrorDispatcher";
import UnknownResponseError from "@/errors/UnknownResponseError";
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
        throw ResponseErrorDispatcher.dispatch(error.response.status, error);
      }
      throw new UnknownResponseError(
        error instanceof Error ? error : undefined
      );
    }
  };
}

export default Service;
