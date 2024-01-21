import Service from "@/services/Service.ts";
import TripsApi from "@/api/TripsApi.ts";
import { CreateTripFormSchemaType } from "@/forms/CreateTripForm";
import CreateTripModel from "@/models/CreateTripModel";
import TripModel from "@/models/TripModel";

class TripsService extends Service {
  public static getTripById = async (uri: string): Promise<TripModel> => {
    return await this.resolveQuery(TripsApi.getTripById(uri));
  };

  public static getTripsByUser = async (uri: string): Promise<TripModel[]> => {
    return await this.resolveQuery(TripsApi.getTripsByUser(uri));
  };

  public static getTripsByUri = async (uri: string): Promise<TripModel[]> => {
    return await this.resolveQuery(TripsApi.getTripsByUser(uri));
  };

  public static createTrip = async (
    trip: CreateTripFormSchemaType
  ): Promise<CreateTripModel> => {
    return await this.resolveQuery(TripsApi.createTrip(trip));
  };

  public static getRecommendedTrips = async (
    uri: string
  ): Promise<TripModel[]> => {
    return await this.resolveQuery(TripsApi.getRecommendedTrips(uri));
  };
}

export default TripsService;
