export class InvalidBasicAuthUserIdError extends Error {
  constructor(message: string = "login.invalid_email") {
    super(message);
    this.name = "InvalidBasicAuthUserIdError";
  }
}
