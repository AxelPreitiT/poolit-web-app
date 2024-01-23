import Service from "@/services/Service.ts";
import TripsApi from "@/api/TripsApi.ts";
import { CreateTripFormSchemaType } from "@/forms/CreateTripForm";
import CreateTripModel from "@/models/CreateTripModel";
import TripModel from "@/models/TripModel";
import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import TripPaginationModel from "@/models/TripPaginationModel.tsx";

class TripsService extends Service {
  public static getTripById = async (uri: string): Promise<TripModel> => {
    return await this.resolveQuery(TripsApi.getTripById(uri));
  };

  public static getTripsByUser = async (uri: string): Promise<TripPaginationModel> => {
    return await this.resolveQuery(TripsApi.getTripsByUser(uri));
  };

  public static getTripsByUri = async (uri: string): Promise<TripPaginationModel> => {
    return await this.resolveQuery(TripsApi.getTripsByUser(uri));
  };

  public static createTrip = async (
    uriTemplate: string,
    trip: CreateTripFormSchemaType
  ): Promise<CreateTripModel> => {
    return await this.resolveQuery(TripsApi.createTrip(uriTemplate, trip));
  };

  public static getRecommendedTrips = async (
    uri: string
  ): Promise<TripModel[]> => {
    return await this.resolveQuery(TripsApi.getRecommendedTrips(uri));
  };

  public static searchTrips = async (
    uriTemplate: string,
    search: SearchTripsFormSchemaType
  ): Promise<TripModel[]> => {
    return await this.resolveQuery(TripsApi.searchTrips(uriTemplate, search));
  };
}

export default TripsService;
