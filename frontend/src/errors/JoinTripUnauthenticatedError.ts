import RequestError from "./RequestError";

class JoinTripUnauthenticatedError extends RequestError {
  private static readonly I18N_KEY: string =
    "query.request.error.join_trip_unauthenticated";
  private static readonly ERROR_ID: string = "JoinTripUnauthenticatedError";

  constructor(message?: string) {
    super(
      JoinTripUnauthenticatedError.I18N_KEY,
      JoinTripUnauthenticatedError.ERROR_ID,
      message
    );
  }
}

export default JoinTripUnauthenticatedError;
