import RequestError from "./RequestError";

class DiscoveryMissingError extends RequestError {
  private static readonly I18N_KEY: string =
    "query.request.error.discovery_missing";
  public static readonly ERROR_ID: string = "DiscoveryMissingError";

  constructor(message: string = "MissingCurrentUserUriError") {
    super(
      DiscoveryMissingError.I18N_KEY,
      DiscoveryMissingError.ERROR_ID,
      message
    );
  }
}

export default DiscoveryMissingError;
