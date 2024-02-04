import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import UnknownResponseError from "./UnknownResponseError";

class ResponseErrorDispatcher {
  private readonly _responseErrorHandlers: Map<
    number,
    new (error: AxiosError) => ResponseError
  > = new Map<number, new (error: AxiosError) => ResponseError>();

  public register(
    statusCode: number,
    handler: new (error: AxiosError) => ResponseError
  ): void {
    this._responseErrorHandlers.set(statusCode, handler);
  }

  public dispatch(statusCode: number, error: AxiosError): ResponseError {
    const handler = this._responseErrorHandlers.get(statusCode);
    if (!handler) {
      return new UnknownResponseError(error);
    }
    return new handler(error);
  }
}

export default new ResponseErrorDispatcher() as ResponseErrorDispatcher;
