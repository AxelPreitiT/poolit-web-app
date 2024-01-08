import ResponseError from "./ResponseError";

const unknownI18nKey = "errors.response.unknown";
const unknownStatusCode = -1;
const unknownStatusText = "Unknown";

class UnknownResponseError extends ResponseError {
  constructor(responseMessage: string = "unknown response error") {
    super(unknownI18nKey, responseMessage);
  }

  public getStatusCode(): number {
    return unknownStatusCode;
  }

  public getStatusText(): string {
    return unknownStatusText;
  }
}

export default UnknownResponseError;
