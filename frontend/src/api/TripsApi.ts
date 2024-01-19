import AxiosApi from "@/api/axios/AxiosApi.ts";
import { CreateTripFormSchemaType } from "@/forms/CreateTripForm";
import CreateTripModel from "@/models/CreateTripModel";
import TripModel from "@/models/TripModel";
import { getIsoDate } from "@/utils/date/isoDate";
import { AxiosPromise, AxiosResponse } from "axios";

type CreateTripDataType = {
  originCityId: number;
  originAddress: string;
  destinationCityId: number;
  destinationAddress: string;
  carId: number;
  maxSeats: number;
  price: number;
  date: string;
  time: string;
  multitrip: boolean;
  lastDate?: string;
};

class TripsApi extends AxiosApi {
  private static readonly TRIPS_BASE_URI: string = "/trips";
  private static readonly TRIPS_CONTENT_TYPE_HEADER: string =
    "application/vnd.trip.v1+json";

  public static getTripById: (uri: string) => AxiosPromise<TripModel> = (
    uri: string
  ) => {
    return this.get<TripModel>(uri, {
      headers: {},
    });
  };

  public static getTripsByUser: (uri: string) => AxiosPromise<TripModel[]> = (
    uri: string
  ) => {
    return this.get<TripModel[]>(uri, {
      headers: {},
    });
  };

  public static createTrip: (
    trip: CreateTripFormSchemaType
  ) => AxiosPromise<CreateTripModel> = (trip: CreateTripFormSchemaType) => {
    const data: CreateTripDataType = {
      originCityId: trip.origin_city,
      originAddress: trip.origin_address,
      destinationCityId: trip.destination_city,
      destinationAddress: trip.destination_address,
      carId: trip.car,
      maxSeats: trip.seats,
      price: trip.price,
      date: getIsoDate(trip.date),
      time: trip.time,
      multitrip: !!trip.multitrip,
    };
    if (trip.last_date && !!trip.multitrip) {
      data.lastDate = getIsoDate(trip.last_date);
    }
    return this.post<CreateTripDataType, CreateTripModel>(
      this.TRIPS_BASE_URI,
      data,
      {
        headers: {
          "Content-Type": TripsApi.TRIPS_CONTENT_TYPE_HEADER,
        },
      }
    ).then((response: AxiosResponse) => {
      const tripUri = response.headers.location as string;
      const newResponse = { ...response, data: { tripUri } as CreateTripModel };
      return newResponse;
    });
  };
}

export default TripsApi;
