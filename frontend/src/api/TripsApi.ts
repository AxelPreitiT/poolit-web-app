import AxiosApi from "@/api/axios/AxiosApi.ts";
import { CreateTripFormSchemaType } from "@/forms/CreateTripForm";
import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import CreateTripModel from "@/models/CreateTripModel";
import TripModel from "@/models/TripModel";
import { getIsoDate } from "@/utils/date/isoDate";
import { AxiosPromise, AxiosResponse } from "axios";
import { parseTemplate } from "url-template";
import PaginationModel from "@/models/PaginationModel.tsx";
import TripSortSearchModel from "@/models/TripSortSearchModel";
import TripPageSearchModel from "@/models/TripPageSearchModel";
import tripEarningModel from "@/models/tripEarningModel.ts";
import JoinTripModel from "@/models/JoinTripModel.ts";

type CreateTripRequestBody = {
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
  private static readonly TRIPS_CONTENT_TYPE_HEADER: string =
    "application/vnd.trip.v1+json";

  private static readonly TRIPS_CONTENT_TYPE_JOIN: string =
      "application/vnd.trip.passenger.v1+json";

  //private static readonly TRIPS_EARNING_TYPE_HEADER: string ="trip.earnings.v1+json"

  public static postDeleteTrip: (
    uri: string
    ) => AxiosPromise<void> = (uri: string) => {
    return this.delete<void>(uri, {
      headers: {
      },
    });
  }

  public static postJoinTrip: (
      uri: string,
      data: JoinTripModel
  ) => AxiosPromise<void> =
    (uri: string, data:JoinTripModel) => {
      return this.post<JoinTripModel, void>(
        uri, data,
        {
          headers: {
            "Content-Type": TripsApi.TRIPS_CONTENT_TYPE_JOIN,
          },
        }
    );
  };

  public static getTripById: (uri: string) => AxiosPromise<TripModel> = (
    uri: string
  ) => {
    return this.get<TripModel>(uri, {
      headers: {
        "Accept": TripsApi.TRIPS_CONTENT_TYPE_HEADER,
      },
    });
  };

  public static getAmountByUri: (uri: string) => AxiosPromise<tripEarningModel> = (
      uri: string
  ) => {
    return this.get<tripEarningModel>(uri, {
      headers: {},
    });
  };

  public static getTripsByUser: (
    uri: string
  ) => AxiosPromise<PaginationModel<TripModel>> = (uri: string) => {
    return this.get<TripModel[]>(uri, {
      headers: {},
    }).then((response: AxiosResponse<TripModel[]>) => {
      const trips = response.data;

      //const parsedLinkHeader = parse(linkHeader);
      //console.log("parsedLink" + parsedLinkHeader)
      //console.log("parsedLink.first" + parsedLinkHeader?.first)
      const first = response.headers.link?.match(/<([^>]*)>; rel="first"/)?.[1];
      const prev = response.headers.link?.match(/<([^>]*)>; rel="prev"/)?.[1];
      const next = response.headers.link?.match(/<([^>]*)>; rel="next"/)?.[1];
      const last = response.headers.link?.match(/<([^>]*)>; rel="last"/)?.[1];
      const total = response.headers["x-total-pages"];
      const newResponse: AxiosResponse<PaginationModel<TripModel>> = {
        ...response,
        data: {
          first: first,
          prev: prev,
          next: next,
          last: last,
          totalPages: total,
          data: trips,
        },
      };
      return newResponse;
    });
  };

  public static createTrip: (
    uriTemplate: string,
    trip: CreateTripFormSchemaType
  ) => AxiosPromise<CreateTripModel> = (
    uriTemplate: string,
    trip: CreateTripFormSchemaType
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    const data: CreateTripRequestBody = {
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
    if (trip.last_date && trip.multitrip) {
      data.lastDate = getIsoDate(trip.last_date);
    }
    return this.post<CreateTripRequestBody, CreateTripModel>(uri, data, {
      headers: {
        "Content-Type": TripsApi.TRIPS_CONTENT_TYPE_HEADER,
      },
    }).then((response: AxiosResponse) => {
      const tripUri = response.headers.location as string;
      const newResponse = { ...response, data: { tripUri } as CreateTripModel };
      return newResponse;
    });
  };

  public static getRecommendedTrips: (
    uri: string
  ) => AxiosPromise<TripModel[]> = (uri: string) => {
    return this.get<TripModel[]>(uri);
  };

  public static searchTrips = (
    uriTemplate: string,
    search: SearchTripsFormSchemaType,
    {
      pageOptions = {},
      sortOptions = {},
    }: {
      pageOptions?: TripPageSearchModel;
      sortOptions?: TripSortSearchModel;
    } = {}
  ): AxiosPromise<PaginationModel<TripModel>> => {
    const baseUri = parseTemplate(uriTemplate).expand({});
    const uri = new URL(baseUri);
    const formatDateTime = (date: Date, time: string) => {
      return getIsoDate(date) + "T" + time;
    };
    uri.searchParams.set("originCityId", search.origin_city.toString());
    uri.searchParams.set(
      "destinationCityId",
      search.destination_city.toString()
    );
    uri.searchParams.set(
      "startDateTime",
      formatDateTime(search.date, search.time)
    );
    if (pageOptions.page && pageOptions.page > 0) {
      const page = pageOptions.page - 1;
      uri.searchParams.set("page", page.toString());
    }
    if (pageOptions.pageSize) {
      uri.searchParams.set("pageSize", pageOptions.pageSize.toString());
    }
    if (search.min_price) {
      uri.searchParams.set("minPrice", search.min_price.toString());
    }
    if (search.max_price) {
      uri.searchParams.set("maxPrice", search.max_price.toString());
    }
    if (search.multitrip && search.last_date) {
      const lastDatetime = new Date(
        getIsoDate(search.last_date) + "T" + search.time
      );
      uri.searchParams.set(
        "endDateTime",
        formatDateTime(lastDatetime, search.time)
      );
    }
    if (search.car_features) {
      search.car_features.forEach((feature) => {
        uri.searchParams.append("carFeatures", feature);
      });
    }
    if (sortOptions.sortTypeId) {
      uri.searchParams.set("sortType", sortOptions.sortTypeId.toString());
    }
    if (sortOptions.descending !== undefined) {
      uri.searchParams.set("descending", sortOptions.descending.toString());
    }
    console.log("uri", uri.toString());
    return this.get<TripModel[]>(uri.toString()).then(this.getPaginationModel);
  };
}

export default TripsApi;
