import { AxiosError } from "axios";
import ResponseError from "./ResponseError";
import ResponseErrorDispatcher from "./ResponseErrorDispatcher";
import BadRequestModel from "@/models/BadRequestModel";

const badRequestStatusCode = 400;

class BadRequestResponseError extends ResponseError {
  private static readonly STATUS_CODE: number = badRequestStatusCode;
  private static readonly STATUS_TEXT: string = "Bad Request";
  private static readonly I18N_KEY: string = "query.response.error.bad_request";
  public static readonly ERROR_ID: string = "BadRequestResponseError";

  private errors: BadRequestModel | undefined;

  constructor(error: AxiosError) {
    super(
      BadRequestResponseError.I18N_KEY,
      BadRequestResponseError.ERROR_ID,
      BadRequestResponseError.STATUS_TEXT
    );
    const errors = error.response?.data
      ? (error.response.data as BadRequestModel)
      : undefined;
    this.errors = errors;
  }

  public getStatusCode(): number {
    return BadRequestResponseError.STATUS_CODE;
  }

  public getStatusText(): string {
    return BadRequestResponseError.STATUS_TEXT;
  }

  public getErrors(): BadRequestModel | undefined {
    return this.errors;
  }
}

ResponseErrorDispatcher.register(badRequestStatusCode, BadRequestResponseError);

export default BadRequestResponseError;
