import Service from "@/services/Service.ts";
import TripsApi from "@/api/TripsApi.ts";
import { CreateTripFormSchemaType } from "@/forms/CreateTripForm";
import CreateTripModel from "@/models/CreateTripModel";
import TripModel from "@/models/TripModel";
import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import PaginationModel from "@/models/PaginationModel.tsx";
import TripSortSearchModel from "@/models/TripSortSearchModel";
import TripPageSearchModel from "@/models/TripPageSearchModel";
import tripEarningModel from "@/models/tripEarningModel.ts";
import JoinTripModel from "@/models/JoinTripModel.ts";
import occupiedSeatsModel from "@/models/occupiedSeatsModel.ts";

class TripsService extends Service {
  public static getTripById = async (uri: string): Promise<TripModel> => {
    return await this.resolveQuery(TripsApi.getTripById(uri));
  };


  public static getOccupiedSeats = async (uri: string): Promise<occupiedSeatsModel> => {
    return await this.resolveQuery(TripsApi.getOccupiedSeats(uri));
  };

  public static getAmountByUri = async (uri: string): Promise<tripEarningModel> => {
    return await this.resolveQuery(TripsApi.getAmountByUri(uri));
  }

  public static getTripsByUser = async (
    uri: string
  ): Promise<PaginationModel<TripModel>> => {
    return await this.resolveQuery(TripsApi.getTripsByUser(uri));
  };

  public static getTripsByUri = async (
    uri: string
  ): Promise<PaginationModel<TripModel>> => {
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
    search: SearchTripsFormSchemaType,
    options: {
      pageOptions?: TripPageSearchModel;
      sortOptions?: TripSortSearchModel;
    } = {}
  ): Promise<PaginationModel<TripModel>> => {
    return await this.resolveQuery(
      TripsApi.searchTrips(uriTemplate, search, options)
    );
  };

  public static postJoinTrip = async (
    uri: string,
    data : JoinTripModel
    ): Promise<void> => {
        return await this.resolveQuery(TripsApi.postJoinTrip(uri, data));
    };

  public static postDeleteTrip = async (
    uri: string
    ): Promise<void> => {
        return await this.resolveQuery(TripsApi.postDeleteTrip(uri));
    };

}

export default TripsService;
