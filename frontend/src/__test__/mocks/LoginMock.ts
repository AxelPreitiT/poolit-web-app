import BaseMock from "./BaseMock";

const user = {
  driverRating:2.3333333333333335,
  imageUri:"http://localhost:8080/paw-2023a-07/api/users/11/image",
  passengerRating:1.0,
  reviewsDriverUri:"http://localhost:8080/paw-2023a-07/api/driver-reviews?forUser=11",
  reviewsPassengerUri:"http://localhost:8080/paw-2023a-07/api/passenger-reviews?forUser=11",
  "selfUri":"http://localhost:8080/paw-2023a-07/api/users/11",
  "surname":"Francois",
  "tripCount":23,
  "userId":11,
  "username":"Gaston",
  "email":"gastonfrancois2000@gmail.com",
  "phone":"3424394741",
  "carsUri":"http://localhost:8080/paw-2023a-07/api/cars?fromUser=11",
  "cityUri":"http://localhost:8080/paw-2023a-07/api/cities/31",
  "futureCreatedTripsUri":"http://localhost:8080/paw-2023a-07/api/trips?createdBy=11&past=false",
  "futureReservedTripsUri":"http://localhost:8080/paw-2023a-07/api/trips?reservedBy=11&past=false",
  "mailLocale":"es",
  "pastCreatedTripsUri":"http://localhost:8080/paw-2023a-07/api/trips?createdBy=11&past=true",
  "pastReservedTripsUri":"http://localhost:8080/paw-2023a-07/api/trips?reservedBy=11&past=true",
  "recommendedTripsUri":"http://localhost:8080/paw-2023a-07/api/trips?recommendedFor=11",
  "reportsApproved":1,
  "reportsPublished":4,
  "reportsReceived":2,
  "reportsRejected":0,
  "role":"DRIVER"
}

class LoginMock extends BaseMock {
  public static mockLogin() {
    return this.get(this.getPath("/"), () => this.plainResponse({ status: this.OK_STATUS }));
  }

  public static mockInvalidLogin() {
    return this.get(this.getPath("/"), () =>
      this.plainResponse({ status: this.UNAUTHORIZED_STATUS })
    );
  }

  public static mockUser() {
    return this.get("/users/50", () =>
        this.jsonResponse({username: "user", roles: ["ROLE_USER"]}, {status: this.OK_STATUS}))
  }

  public static mockNonVerifiedLogin() {
    return this.get(this.getPath("/"), () =>
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
