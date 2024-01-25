interface DiscoveryModel {
  carBrandsUriTemplate: string; // [baseUrl]/car-brands{/brandId}
  carFeaturesUriTemplate: string; // [baseUrl]/car-features{/featureId}
  carsUriTemplate: string; // [baseUrl]/cars{/carId}
  citiesUriTemplate: string; // [baseUrl]/cities{/cityId}
  driverReviewsUriTemplate: string; // [baseUrl]/driver-reviews{/reviewId}
  passengerReviewsUriTemplate: string; // [baseUrl]/passenger-reviews{/reviewId}
  reportsUriTemplate: string; // [baseUrl]/reports{/reportId}
  tripsUriTemplate: string; // [baseUrl]/trips{/tripId}
  usersUriTemplate: string; // [baseUrl]/users/{userId}
  tripSortTypesUriTemplate: string; // [baseUrl]/trip-sort-types{/sortTypeId}
}

export default DiscoveryModel;
