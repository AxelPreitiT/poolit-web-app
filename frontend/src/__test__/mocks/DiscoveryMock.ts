import BaseMock from "./BaseMock";

class DiscoveryMock extends BaseMock {
  private static readonly DISCOVERY_LIST = {
    carBrandsUri: this.getPath("/car-brands{/brandId}"),
    carFeaturesUri: this.getPath("/car-features{/featureId}"),
    carsUri: this.getPath("/cars{/carId}{?fromUser}"),
    citiesUri: this.getPath("/cities{/cityId}"),
    driverReviewsUri: this.getPath(
      "/driver-reviews{/reviewId}{?forUser,madeBy,forTrip,page,pageSize}"
    ),
    passengerReviewsUri: this.getPath(
      "/passenger-reviews{/reviewId}{?forUser,madeBy,forTrip,page,pageSize}"
    ),
    reportsUri: this.getPath("/reports{/reportId}{?madeBy,forTrip}"),
    tripSortTypesUri: this.getPath("/trip-sort-types{/sortTypeId}"),
    tripsUri: this.getPath(
      "/trips{/tripId}{?originCityId,destinationCityId,startDateTime,endDateTime,minPrice,maxPrice,carFeatures*,sortType,createdBy,reservedBy,recommendedFor,past,descending,page,pageSize}"
    ),
    usersUri: this.getPath("/users/{userId}"),
  };

  public static mockDiscovery() {
    return this.getWithoutBase("/", () =>
      this.jsonResponse(this.DISCOVERY_LIST, { status: this.OK_STATUS })
    );
  }
}

export default DiscoveryMock;
