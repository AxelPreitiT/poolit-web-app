import DiscoveryModel from "@/models/DiscoveryModel";
import BaseMock from "./BaseMock";
import { HttpHandler } from "msw";
import MockInjector from "./MockInjector";

const discoveryList: DiscoveryModel = {
  carBrandsUriTemplate: "localhost:8080/carBrands{/brandId}",
  carFeaturesUriTemplate: "localhost:8080/carFeatures{/featureId}",
  carsUriTemplate: "localhost:8080/cars{/carId}{?fromUser}",
  citiesUriTemplate: "localhost:8080/cities{/cityId}",
  driverReviewsUriTemplate:
    "localhost:8080/driverReviews{/driverReviewId}{?forUser,madeBy,forTrip,page,pageSize}",
  passengerReviewsUriTemplate:
    "localhost:8080/passengerReviews{/passengerReviewId}{?forUser,madeBy,forTrip,page,pageSize}",
  reportsUriTemplate: "localhost:8080/reports{/reportId}{?madeBy,forTrip}",
  tripSortTypesUriTemplate: "localhost:8080/tripSortTypes{/tripSortTypeId}",
  tripsUriTemplate:
    "localhost:8080/trips{/tripId}{?originCityId,destinationCityId,startDateTime,endDateTime,minPrice,maxPrice,carFeatures,sortType,createdBy,reservedBy,recommendedFor,past,descending,page,pageSize}",
  usersUriTemplate: "localhost:8080/users{/userId}",
};

class DiscoveryMock extends BaseMock {
  public static mockDiscovery() {
    return this.get("/", () =>
      this.jsonResponse(discoveryList, { status: this.OK_STATUS })
    );
  }

  public static injectMockHandlers(): HttpHandler[] {
    return [this.mockDiscovery()];
  }
}

MockInjector.registerMock(DiscoveryMock);

export default DiscoveryMock;
