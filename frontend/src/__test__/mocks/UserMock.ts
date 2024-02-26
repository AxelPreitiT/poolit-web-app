import BaseMock from "@/__test__/mocks/BaseMock.ts";
import UserPrivateModel from "@/models/UserPrivateModel.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import UserDriverModel from "@/models/UserDriverModel.ts";

export const profileListInfo = {
  title: "Title component",
  btn_footer_text: "btn footer text",
  empty_text: "Empty text",
  empty_icon: "book",
  id: 5,
};

class UserMock extends BaseMock {
  public static readonly PRIVATE_USER_ROLE_USER: UserPrivateModel = {
    driverRating: 3.0,
    imageUri: this.getPath("/users/50/image"),
    passengerRating: 0.0,
    reviewsDriverUri: this.getPath("/driver-reviews?forUser=50"),
    reviewsPassengerUri: this.getPath("/passenger-reviews?forUser=50"),
    selfUri: this.getPath("/users/50"),
    surname: "Mentasti",
    tripCount: 4,
    userId: 50,
    username: "Jose Rodolfo",
    email: "jmentasti+testapi@itba.edu.ar",
    phone: "1139150686",
    carsUri: this.getPath("/cars?fromUser=50"),
    cityUri: this.getPath("/cities/5"),
    futureCreatedTripsUri: this.getPath("/trips?createdBy=50&past=false"),
    futureReservedTripsUri: this.getPath("/trips?reservedBy=50&past=false"),
    mailLocale: "en",
    pastCreatedTripsUri: this.getPath("/trips?createdBy=50&past=true"),
    pastReservedTripsUri: this.getPath("/trips?reservedBy=50&past=true"),
    recommendedTripsUri: this.getPath("/trips?recommendedFor=50"),
    reportsApproved: 0,
    reportsPublished: 1,
    reportsReceived: 0,
    reportsRejected: 1,
    role: "USER",
  };

  public static readonly PRIVATE_USER_ROLE_DRIVER: UserPrivateModel = {
    driverRating: 3.0,
    imageUri: this.getPath("/users/50/image"),
    passengerRating: 0.0,
    reviewsDriverUri: this.getPath("/driver-reviews?forUser=50"),
    reviewsPassengerUri: this.getPath("/passenger-reviews?forUser=50"),
    selfUri: this.getPath("/users/50"),
    surname: "Mentasti",
    tripCount: 4,
    userId: 50,
    username: "Jose Rodolfo",
    email: "jmentasti+testapi@itba.edu.ar",
    phone: "1139150686",
    carsUri: this.getPath("/cars?fromUser=50"),
    cityUri: this.getPath("/cities/5"),
    futureCreatedTripsUri: this.getPath("/trips?createdBy=50&past=false"),
    futureReservedTripsUri: this.getPath("/trips?reservedBy=50&past=false"),
    mailLocale: "en",
    pastCreatedTripsUri: this.getPath("/trips?createdBy=50&past=true"),
    pastReservedTripsUri: this.getPath("/trips?reservedBy=50&past=true"),
    recommendedTripsUri: this.getPath("/trips?recommendedFor=50"),
    reportsApproved: 0,
    reportsPublished: 1,
    reportsReceived: 0,
    reportsRejected: 1,
    role: "DRIVER",
  };

  public static readonly PRIVATE_USER_ROLE_ADMIN: UserPrivateModel = {
    driverRating: 3.0,
    imageUri: this.getPath("/users/50/image"),
    passengerRating: 0.0,
    reviewsDriverUri: this.getPath("/driver-reviews?forUser=50"),
    reviewsPassengerUri: this.getPath("/passenger-reviews?forUser=50"),
    selfUri: this.getPath("/users/50"),
    surname: "Mentasti",
    tripCount: 4,
    userId: 50,
    username: "Gaston",
    email: "jmentasti+testapi@itba.edu.ar",
    phone: "1139150686",
    carsUri: this.getPath("/cars?fromUser=50"),
    cityUri: this.getPath("/cities/5"),
    futureCreatedTripsUri: this.getPath("/trips?createdBy=50&past=false"),
    futureReservedTripsUri: this.getPath("/trips?reservedBy=50&past=false"),
    mailLocale: "en",
    pastCreatedTripsUri: this.getPath("/trips?createdBy=50&past=true"),
    pastReservedTripsUri: this.getPath("/trips?reservedBy=50&past=true"),
    recommendedTripsUri: this.getPath("/trips?recommendedFor=50"),
    reportsApproved: 0,
    reportsPublished: 1,
    reportsReceived: 0,
    reportsRejected: 1,
    role: "ADMIN",
  };

  public static readonly PUBLIC_USER: UserPublicModel = {
    driverRating: 3.0,
    imageUri: this.getPath("/users/50/image"),
    passengerRating: 0.0,
    reviewsDriverUri: this.getPath("/driver-reviews?forUser=50"),
    reviewsPassengerUri: this.getPath("/passenger-reviews?forUser=50"),
    selfUri: this.getPath("/users/50"),
    surname: "Mentasti",
    tripCount: 4,
    userId: 50,
    username: "Jose Rodolfo",
  };

  public static readonly PUBLIC_DRIVER: UserDriverModel = {
    driverRating: 3.0,
    imageUri: this.getPath("/users/50/image"),
    passengerRating: 0.0,
    reviewsDriverUri: this.getPath("/driver-reviews?forUser=50"),
    reviewsPassengerUri: this.getPath("/passenger-reviews?forUser=50"),
    selfUri: this.getPath("/users/50"),
    surname: "Mentasti",
    tripCount: 4,
    userId: 50,
    username: "Jose Rodolfo",
    phone: "1139150686",
    email: "jmentasti+testapi@itba.edu.ar",
  };

  public static getByIdPrivateRoleAdmin() {
    return this.get("/users/:userId", () =>
      this.jsonResponse(this.PRIVATE_USER_ROLE_ADMIN, {
        status: this.OK_STATUS,
      })
    );
  }
  public static getByIdPrivateRoleUser() {
    return this.get("/users/:userId", () =>
      this.jsonResponse(this.PRIVATE_USER_ROLE_USER, { status: this.OK_STATUS })
    );
  }
  public static optionsMock() {
    return this.options("/users/:userId", () =>
      this.plainResponse({
        status: this.OK_STATUS,
        headers: {
          "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Expose-Headers":
            "JWT-authorization, JWT-refresh-authorization, Account-verification, Accept, X-Total-Pages, Link, Location",
        },
      })
    );
  }
  public static getByIdPrivateRoleDriver() {
    return this.get("/users/:userId", () =>
      this.jsonResponse(this.PRIVATE_USER_ROLE_DRIVER, {
        status: this.OK_STATUS,
      })
    );
  }
  public static getByIdPublic() {
    return this.get("/users/:userId", () =>
      this.jsonResponse(this.PUBLIC_USER, { status: this.OK_STATUS })
    );
  }

  public static getByIdDriver() {
    return this.get("/users/:userId", () =>
      this.jsonResponse(this.PUBLIC_DRIVER, { status: this.OK_STATUS })
    );
  }

  public static getEmptyUserImage() {
    return this.get("/users/:userId/image", () =>
      this.plainResponse({ status: this.NO_CONTENT_STATUS })
    );
  }

  public static createUserSuccess() {
    return this.post("/users", () =>
      this.plainResponse({ status: this.CREATED_STATUS })
    );
  }

  public static createUserEmailAlreadyExists() {
    return this.post("/users", () =>
      this.plainResponse({ status: this.CONFLICT_STATUS })
    );
  }

  public static updateUserSuccess() {
    return this.patch("/users/:userId", () =>
      this.plainResponse({ status: this.NO_CONTENT_STATUS })
    );
  }

  public static updateUserFail() {
    return this.patch("/users/:userId", () =>
      this.plainResponse({ status: this.INTERNAL_SERVER_ERROR_STATUS })
    );
  }

  public static updateUserImage() {
    return this.put("/users/:userId/image", () =>
      this.plainResponse({ status: this.NO_CONTENT_STATUS })
    );
  }
}

export default UserMock;
