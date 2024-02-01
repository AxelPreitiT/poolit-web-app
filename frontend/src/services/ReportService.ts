import Service from "@/services/Service.ts";
import ReportsApi from "@/api/ReportsApi.ts";
import PrivateReportModel from "@/models/PrivateReportModel.ts";
import ReportOptionModel from "@/models/ReportOptionModel";
import { getReportsOptionsByRelation } from "@/enums/ReportsOptions";
import ReportRelation from "@/enums/ReportRelation";
import { ReportFormSchemaType } from "@/forms/ReportForm";
import { DecideReportFormSchemaType } from "@/forms/DecideReportForm";
import PaginationModel from "@/models/PaginationModel.tsx";

class ReportService extends Service {
  public static getReports = async (
    uri: string
  ): Promise<PaginationModel<PrivateReportModel>> => {
    return await this.resolveQuery(ReportsApi.getReports(uri));
  };

  public static getReport = async (
    uri: string
  ): Promise<PrivateReportModel> => {
    return await this.resolveQuery(ReportsApi.getReport(uri));
  };

  public static getReportOptionsByRelation = async (
    reportRelation: ReportRelation
  ): Promise<ReportOptionModel[]> => {
    return getReportsOptionsByRelation(reportRelation);
  };

  public static createReport = async (
    uriTemplate: string,
    tripId: number,
    reportedId: number,
    relation: ReportRelation,
    data: ReportFormSchemaType
  ): Promise<void> => {
    await this.resolveQuery(
      ReportsApi.createReport(uriTemplate, tripId, reportedId, relation, data)
    );
  };

  public static approveReport = async (
    uri: string,
    data: DecideReportFormSchemaType
  ): Promise<void> => {
    await this.resolveQuery(ReportsApi.approveReport(uri, data));
  };

  public static rejectReport = async (
    uri: string,
    data: DecideReportFormSchemaType
  ): Promise<void> => {
    await this.resolveQuery(ReportsApi.rejectReport(uri, data));
  };
}

export default ReportService;
