import DiscoveryModel from "@/models/DiscoveryModel";
import AxiosApi from "./axios/AxiosApi";
import { AxiosPromise, AxiosRequestConfig, AxiosResponse } from "axios";

interface DiscoveryResponse {
  carBrandsUri: string; // [baseUrl]/car-brands{/brandId}
  carFeaturesUri: string; // [baseUrl]/car-features{/featureId}
  carsUri: string; // [baseUrl]/cars{/carId}
  citiesUri: string; // [baseUrl]/cities{/cityId}
  driverReviewsUri: string; // [baseUrl]/driver-reviews{/reviewId}
  passengerReviewsUri: string; // [baseUrl]/passenger-reviews{/reviewId}
  reportsUri: string; // [baseUrl]/reports{/reportId}
  tripsUri: string; // [baseUrl]/trips{/tripId}
  usersUri: string; // [baseUrl]/users/{userId}
  tripSortTypesUri: string; // [baseUrl]/trip-sort-types{/sortTypeId}
}

class DiscoveryApi extends AxiosApi {
  private static mapResponseToModel: (
    response: AxiosResponse<DiscoveryResponse>
  ) => AxiosResponse<DiscoveryModel> = (
    response: AxiosResponse<DiscoveryResponse>
  ) => {
    const discovery: DiscoveryResponse = response.data;
    const discoveryModel: DiscoveryModel = {
      carBrandsUriTemplate: discovery.carBrandsUri,
      carFeaturesUriTemplate: discovery.carFeaturesUri,
      carsUriTemplate: discovery.carsUri,
      citiesUriTemplate: discovery.citiesUri,
      driverReviewsUriTemplate: discovery.driverReviewsUri,
      passengerReviewsUriTemplate: discovery.passengerReviewsUri,
      reportsUriTemplate: discovery.reportsUri,
      tripsUriTemplate: discovery.tripsUri,
      usersUriTemplate: discovery.usersUri,
      tripSortTypesUriTemplate: discovery.tripSortTypesUri,
    };
    return {
      ...response,
      data: discoveryModel,
    };
  };

  public static getDiscovery: (
    config?: AxiosRequestConfig
  ) => AxiosPromise<DiscoveryModel> = async (config?: AxiosRequestConfig) => {
    return this.get<DiscoveryResponse>("/", config).then(
      this.mapResponseToModel
    );
  };
}

export default DiscoveryApi;
