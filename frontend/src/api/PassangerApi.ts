import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise, AxiosResponse } from "axios";
import PassangerModel from "@/models/PassangerModel.ts";
import PaginationModel from "@/models/PaginationModel.ts";
import { parseTemplate } from "url-template";
import PassangerStatus from "@/enums/PassangerStatus.ts";
import ReportModel from "@/models/ReportModel.ts";

type AcceptRejecPassangerBody = {
  passengerState: string;
};

class PassangerApi extends AxiosApi {
  private static readonly PASSENGER_LIST_TYPE =
    "application/vnd.trip.passenger.list.v1+json";

  private static readonly PASSANGER_CONTENT_TYPE_HEADER: string =
    "application/vnd.trip.passenger.state.v1+json";
  private static readonly REPORT_LIST_TYPE =
    "application/vnd.report.public.list.v1+json";
  private static readonly PASSENGER_TYPE: string =
      "application/vnd.trip.passenger.v1+json";


  // private static readonly REPORT_HEADER: string = "application/vnd.report.public.v1+json";

  // private static readonly PASSENGER_HEADER: string = "application/vnd.trip.passenger.v1+json";

  public static getPassangerByUri: (
    uri: string
  ) => AxiosPromise<PaginationModel<PassangerModel>> = (uri: string) => {
    const newUri = parseTemplate(uri).expand({});
    return this.get<PassangerModel[]>(newUri, {
      headers: {
        Accept: PassangerApi.PASSENGER_LIST_TYPE,
      },
    }).then((response: AxiosResponse<PassangerModel[]>) => {
      const passangers = response.data;

      const first = response.headers.link?.match(/<([^>]*)>; rel="first"/)?.[1];
      const prev = response.headers.link?.match(/<([^>]*)>; rel="prev"/)?.[1];
      const next = response.headers.link?.match(/<([^>]*)>; rel="next"/)?.[1];
      const last = response.headers.link?.match(/<([^>]*)>; rel="last"/)?.[1];
      const total = response.headers["x-total-pages"];
      const newResponse: AxiosResponse<PaginationModel<PassangerModel>> = {
        ...response,
        data: {
          first: first,
          prev: prev,
          next: next,
          last: last,
          totalPages: total,
          data: passangers,
        },
      };
      return newResponse;
    });
  };

  public static getReport: (uri: string) => Promise<boolean> = (
    uri: string
  ) => {
    return this.get<ReportModel>(uri, {
      headers: {
        Accept: PassangerApi.REPORT_LIST_TYPE,
      },
    }).then((response: AxiosResponse<ReportModel>) => {
      return !(response.status === 204);
    });
  };

  public static getReviewPassanger: (uri: string) => Promise<boolean> = (
    uri: string
  ) => {
    return this.get<PassangerModel>(uri, {
      headers: {},
    }).then((response: AxiosResponse<PassangerModel>) => {
      return !(response.status === 204);
    });
  };

  public static patchAcceptPassangerByUri: (uri: string) => AxiosPromise<void> =
    (uri: string) => {
      return this.patch<AcceptRejecPassangerBody, void>(
        uri,
        { passengerState: PassangerStatus.ACCEPTED as string },
        {
          headers: {
            "Content-Type": PassangerApi.PASSANGER_CONTENT_TYPE_HEADER,
          },
        }
      );
    };

  public static patchRejectPassangerByUri: (uri: string) => AxiosPromise<void> =
    (uri: string) => {
      return this.patch<AcceptRejecPassangerBody, void>(
        uri,
        { passengerState: PassangerStatus.REJECTED as string },
        {
          headers: {
            "Content-Type": PassangerApi.PASSANGER_CONTENT_TYPE_HEADER,
          },
        }
      );
    };

  public static getPassangerRole: (
    uri: string
  ) => AxiosPromise<PassangerModel> = (uri: string) => {
    return this.get<PassangerModel>(uri, {
      headers: {
          "Accept" : PassangerApi.PASSENGER_TYPE
        },
    });
  };

  public static deleteCancelTrip: (uri: string) => AxiosPromise<void> = (
    uri: string
  ) => {
    return this.delete<void>(uri, {
      headers: {},
    });
  };
}

export default PassangerApi;
