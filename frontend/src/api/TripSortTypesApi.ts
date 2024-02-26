import TripSortTypeModel from "@/models/TripSortTypeModel";
import AxiosApi from "./axios/AxiosApi";
import { parseTemplate } from "url-template";
import { AxiosPromise } from "axios";

class TripSortTypesApi extends AxiosApi {
  private static readonly TRIP_SORT_TYPE_ACCEPT_HEADER = "application/vnd.trip-sort-type.v1+json";
  private static readonly TRIP_SORT_TYPE_LIST_ACCEPT_HEADER = "application/vnd.trip-sort-type.list.v1+json";
  private static readonly TRIP_SORT_TYPE_TEMPLATE_KEY: string = "/sortTypeId";

  public static getTripSortTypes: (
    uriTemplate: string
  ) => AxiosPromise<TripSortTypeModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<TripSortTypeModel[]>(uri,{
      headers:{
        Accept:TripSortTypesApi.TRIP_SORT_TYPE_LIST_ACCEPT_HEADER
      }
    });
  };

  public static getTripSortTypeById: (
    uriTemplate: string,
    sortTypeId: string
  ) => AxiosPromise<TripSortTypeModel> = (
    uriTemplate: string,
    sortTypeId: string
  ) => {
    const uri = parseTemplate(uriTemplate).expand({
      [this.TRIP_SORT_TYPE_TEMPLATE_KEY]: sortTypeId,
    });
    return this.get<TripSortTypeModel>(uri,{
      headers:{
        Accept:TripSortTypesApi.TRIP_SORT_TYPE_ACCEPT_HEADER
      }
    });
  };
}

export default TripSortTypesApi;
