// import DiscoveryModel from "@/models/DiscoveryModel";
import BaseMock from "./BaseMock";

const discoveryList: any = {
  "carBrandsUri": "http://localhost:8080/paw-2023a-07/api/car-brands{/brandId}",
  "carFeaturesUri": "http://localhost:8080/paw-2023a-07/api/car-features{/featureId}",
  "carsUri": "http://localhost:8080/paw-2023a-07/api/cars{/carId}{?fromUser}",
  "citiesUri": "http://localhost:8080/paw-2023a-07/api/cities{/cityId}",
  "driverReviewsUri": "http://localhost:8080/paw-2023a-07/api/driver-reviews{/reviewId}{?forUser,madeBy,forTrip,page,pageSize}",
  "passengerReviewsUri": "http://localhost:8080/paw-2023a-07/api/passenger-reviews{/reviewId}{?forUser,madeBy,forTrip,page,pageSize}",
  "reportsUri": "http://localhost:8080/paw-2023a-07/api/reports{/reportId}{?madeBy,forTrip}",
  "tripSortTypesUri": "http://localhost:8080/paw-2023a-07/api/trip-sort-types{/sortTypeId}",
  "tripsUri": "http://localhost:8080/paw-2023a-07/api/trips{/tripId}{?originCityId,destinationCityId,startDateTime,endDateTime,minPrice,maxPrice,carFeatures*,sortType,createdBy,reservedBy,recommendedFor,past,descending,page,pageSize}",
  "usersUri": "http://localhost:8080/paw-2023a-07/api/users/{userId}"
}

class DiscoveryMock extends BaseMock {
  public static mockDiscovery() {
    return this.getWithoutBase("/", () =>
      this.jsonResponse(discoveryList, { status: this.OK_STATUS })
    );
  }
}

export default DiscoveryMock;
