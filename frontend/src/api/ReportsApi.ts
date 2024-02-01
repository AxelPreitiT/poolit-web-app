import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise, AxiosResponse } from "axios";
import PrivateReportModel from "@/models/PrivateReportModel.ts";
import ReportRelation from "@/enums/ReportRelation";
import { ReportFormSchemaType } from "@/forms/ReportForm";
import { DecideReportFormSchemaType } from "@/forms/DecideReportForm";
import ReportState from "@/enums/ReportState";
import PaginationModel from "@/models/PaginationModel.tsx";
import { parseTemplate } from "url-template";

class ReportsApi extends AxiosApi {
  private static readonly REPORTS_PRIVATE_LIST_ACCEPT_HEADER: string =
      "application/vnd.report.private.list.v1+json";
  private static readonly REPORTS_PRIVATE_ACCEPT_HEADER: string =
    "application/vnd.report.private.v1+json";

  private static readonly CREATE_REPORT_CONTENT_TYPE: string =
    "application/vnd.report.v1+json";

  private static readonly DECIDE_REPORT_CONTENT_TYPE: string =
    "application/vnd.report.decision.v1+json";

  public static getReports: (
    uri: string
  ) => AxiosPromise<PaginationModel<PrivateReportModel>> = (uri: string) => {
    return this.get<PrivateReportModel[]>(uri, {
      headers: {
        Accept: ReportsApi.REPORTS_PRIVATE_LIST_ACCEPT_HEADER,
      },
    }).then((response: AxiosResponse<PrivateReportModel[]>) => {
      const reports = response.data;

      const first = response.headers.link?.match(/<([^>]*)>; rel="first"/)?.[1];
      const prev = response.headers.link?.match(/<([^>]*)>; rel="prev"/)?.[1];
      const next = response.headers.link?.match(/<([^>]*)>; rel="next"/)?.[1];
      const last = response.headers.link?.match(/<([^>]*)>; rel="last"/)?.[1];
      const total = response.headers["x-total-pages"];
      const newResponse: AxiosResponse<PaginationModel<PrivateReportModel>> = {
        ...response,
        data: {
          first: first,
          prev: prev,
          next: next,
          last: last,
          totalPages: total,
          data: reports,
        },
      };
      return newResponse;
    });
  };

  public static getReport: (uri: string) => AxiosPromise<PrivateReportModel> = (
    uri: string
  ) => {
    return this.get<PrivateReportModel>(uri, {
      headers: {
        Accept: ReportsApi.REPORTS_PRIVATE_ACCEPT_HEADER,
      },
    });
  };

  public static createReport: (
    uriTemplate: string,
    tripId: number,
    reportedId: number,
    relation: ReportRelation,
    data: ReportFormSchemaType
  ) => AxiosPromise<void> = (
    uriTemplate: string,
    tripId: number,
    reportedId: number,
    relation: ReportRelation,
    { reason, option }
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    const body = {
      tripId,
      reportedId,
      relation,
      reason: option,
      comment: reason,
    };
    return this.post(uri, body, {
      headers: {
        "Content-Type": ReportsApi.CREATE_REPORT_CONTENT_TYPE,
      },
    });
  };

  private static decideReport: (
    uri: string,
    reportState: ReportState,
    data: DecideReportFormSchemaType
  ) => AxiosPromise<void> = (
    uri: string,
    reportState: ReportState,
    { reason }
  ) => {
    const body = {
      reason,
      reportState,
    };
    return this.put(uri, body, {
      headers: {
        "Content-Type": ReportsApi.DECIDE_REPORT_CONTENT_TYPE,
      },
    });
  };

  public static approveReport: (
    uri: string,
    data: DecideReportFormSchemaType
  ) => AxiosPromise<void> = (uri: string, data: DecideReportFormSchemaType) => {
    return this.decideReport(uri, ReportState.APPROVED, data);
  };

  public static rejectReport: (
    uri: string,
    data: DecideReportFormSchemaType
  ) => AxiosPromise<void> = (uri: string, data: DecideReportFormSchemaType) => {
    return this.decideReport(uri, ReportState.REJECTED, data);
  };
}

export default ReportsApi;
