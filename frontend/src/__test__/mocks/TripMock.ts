import BaseMock from "@/__test__/mocks/BaseMock.ts";
import OccupiedSeatsModel from "@/models/occupiedSeatsModel.ts";
import TripModel from "@/models/TripModel.ts";
import PassangerModel from "@/models/PassangerModel.ts";

const occupiedSeats: OccupiedSeatsModel = {
  occupiedSeats: 10,
};

class TripMock extends BaseMock {
  public static readonly TRIPS: TripModel[] = [
    {
      carReviewsUriTemplate: this.getPath(
        "/cars/1/reviews?forTrip=144&madeBy={userId}"
      ),
      carUri: this.getPath("/cars/1"),
      deleted: false,
      destinationAddress: "Agronomia",
      destinationCityUri: this.getPath("/cities/1"),
      driverReportsUriTemplate: this.getPath(
        "/reports?forTrip=144&forUser=1&madeBy={userId}"
      ),
      driverReviewsUriTemplate: this.getPath(
        "/driver-reviews?forTrip=144&madeBy={userId}"
      ),
      driverUri: this.getPath("/users/40"),
      endDateTime: "2024-02-03T18:00",
      maxSeats: "4",
      originAddress: "Av Callao 1348",
      originCityUri: this.getPath("/cities/28"),
      passengersUriTemplate: this.getPath(
        "/trips/144/passengers{/userId}{?startDateTime,endDateTime,passengerState}"
      ),
      pricePerTrip: 1000.0,
      selfUri: this.getPath("/trips/144"),
      startDateTime: "2024-02-03T18:00",
      tripId: 144,
      tripStatus: "NOT_STARTED",
    },
    {
      carReviewsUriTemplate: this.getPath(
        "/cars/15/reviews?forTrip=143&madeBy={userId}"
      ),
      carUri: this.getPath("/cars/15"),
      deleted: false,
      destinationAddress: "Barracas",
      destinationCityUri: this.getPath("/cities/4"),
      driverReportsUriTemplate: this.getPath(
        "/reports?forTrip=143&forUser=1&madeBy={userId}"
      ),
      driverReviewsUriTemplate: this.getPath(
        "/driver-reviews?forTrip=143&madeBy={userId}"
      ),
      driverUri: this.getPath("/users/1"),
      endDateTime: "2024-02-03T18:00",
      maxSeats: "4",
      originAddress: "Av Callao 1348",
      originCityUri: this.getPath("/cities/28"),
      passengersUriTemplate: this.getPath(
        "/trips/143/passengers{/userId}{?startDateTime,endDateTime,passengerState}"
      ),
      pricePerTrip: 1300.0,
      selfUri: this.getPath("/trips/143"),
      startDateTime: "2024-02-03T18:00",
      tripId: 143,
      tripStatus: "NOT_STARTED",
    },
  ];

  public static PASSENGER_LIST: PassangerModel[] = [
    {
      endDateTime: "2023-12-28T22:00",
      otherPassengersUriTemplate: this.getPath(
        "/trips/139/passengers?startDateTime=2023-12-28T22%3A00&endDateTime=2023-12-28T22%3A00&passengerState=ACCEPTED{&excluding*}"
      ),
      passengerReportsForTripUriTemplate: this.getPath(
        "/reports?forTrip=139&forUser=1&madeBy={userId}"
      ),
      passengerReviewsForTripUriTemplate: this.getPath(
        "/passenger-reviews?forTrip=139&forUser=1&madeBy={userId}"
      ),
      passengerState: "ACCEPTED",
      selfUri: this.getPath("/trips/139/passengers/1"),
      startDateTime: "2023-12-28T22:00",
      tripUri: this.getPath(
        "/trips/139?startDateTime=2023-12-28T22%3A00&endDateTime=2023-12-28T22%3A00"
      ),
      userId: "1",
      userUri: this.getPath("/users/1"),
    },
  ];

  public static getTripByIdProp(tripId: number): TripModel {
    return this.TRIPS.find((trip) => trip.tripId === tripId) || this.TRIPS[0];
  }

  public static getPassegerByIdProp(userId: string): PassangerModel {
    return (
      this.PASSENGER_LIST.find((passenger) => passenger.userId === userId) ||
      this.PASSENGER_LIST[0]
    );
  }

  public static mockEmptyTrips() {
    return this.get("/trips", () =>
      this.plainResponse({ status: this.NO_CONTENT_STATUS })
    );
  }

  public static mockOccupiedSeats() {
    return this.get("/trips/:tripId/passengers", () =>
      this.jsonResponse(occupiedSeats, { status: this.OK_STATUS })
    );
  }

  public static mockTripList() {
    return this.get("/trips", () =>
      this.jsonResponse(this.TRIPS, { status: this.OK_STATUS })
    );
  }

  public static mockSingleTrip() {
    return this.get("/trips/:id", () =>
      this.jsonResponse(this.getTripByIdProp(144), { status: this.OK_STATUS })
    );
  }
  public static mockPassengerList() {
    return this.get("/trips/:tripId/passengers", () =>
      this.jsonResponse(this.PASSENGER_LIST, { status: this.OK_STATUS })
    );
  }
  public static mockPassenger() {
    return this.get("/trips/:tripId/passengers/:userId", () =>
      this.jsonResponse(this.getPassegerByIdProp("1"), {
        status: this.OK_STATUS,
      })
    );
  }
}

export default TripMock;
