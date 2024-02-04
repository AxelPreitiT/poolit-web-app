import BaseMock from "./BaseMock";

class LoginMock extends BaseMock {
  public static mockLogin() {
    return this.getWithoutBase("/", () =>
      this.plainResponse({ status: this.OK_STATUS })
    );
  }

  public static mockInvalidLogin() {
    return this.getWithoutBase("/", () =>
      this.plainResponse({ status: this.UNAUTHORIZED_STATUS })
    );
  }

  public static mockUser() {
    return this.get("/users/50", () =>
      this.jsonResponse(
        { username: "user", roles: ["ROLE_USER"] },
        { status: this.OK_STATUS }
      )
    );
  }

  public static mockNonVerifiedLogin() {
    return this.getWithoutBase("/", () =>
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
