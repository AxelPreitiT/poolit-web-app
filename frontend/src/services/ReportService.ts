import Service from "@/services/Service.ts";
import ReportsApi from "@/api/ReportsApi.ts";
import PrivateReportModel from "@/models/PrivateReportModel.ts";
import ReportOptionModel from "@/models/ReportOptionModel";
import { getReportsOptions } from "@/enums/ReportsOptions";

class ReportService extends Service {
  public static getReports = async (
    uri: string
  ): Promise<PrivateReportModel[]> => {
    return await this.resolveQuery(ReportsApi.getReports(uri));
  };

  public static getReport = async (
    uri: string
  ): Promise<PrivateReportModel> => {
    return await this.resolveQuery(ReportsApi.getReport(uri));
  };

  public static getReportOptions = async (): Promise<ReportOptionModel[]> => {
    return getReportsOptions();
  };
}

export default ReportService;
