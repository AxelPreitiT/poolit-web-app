import BaseMock from "./BaseMock";

class LoginMock extends BaseMock {
  public static mockLogin() {
    return this.get("/", () => this.plainResponse({ status: this.OK_STATUS }));
  }

  public static mockInvalidLogin() {
    return this.get("/", () =>
      this.plainResponse({ status: this.UNAUTHORIZED_STATUS })
    );
  }

  public static mockNonVerifiedLogin() {
    return this.get("/", () =>
      this.plainResponse({
        status: this.UNAUTHORIZED_STATUS,
        headers: {
          "account-verification": "True",
        },
      })
    );
  }
}

export default LoginMock;
