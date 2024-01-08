import ResponseError from "./ResponseError";
import UnknownResponseError from "./UnknownResponseError";

class ResponseErrorDispatcher {
  private readonly _responseErrorHandlers: Map<
    number,
    new (responseMessage?: string) => ResponseError
  > = new Map<number, new (responseMessage?: string) => ResponseError>();

  public register(
    statusCode: number,
    handler: new (responseMessage?: string) => ResponseError
  ): void {
    this._responseErrorHandlers.set(statusCode, handler);
  }

  public dispatch(statusCode: number, responseMessage?: string): ResponseError {
    const handler =
      this._responseErrorHandlers.get(statusCode) || UnknownResponseError;
    return new handler(responseMessage);
  }
}

export default new ResponseErrorDispatcher() as ResponseErrorDispatcher;
