import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise} from "axios";
import PrivateReportModel from "@/models/PrivateReportModel.ts"

class ReportsApi extends AxiosApi{
    private static readonly REPORTS_PRIVATE_ACCEPT_HEADER: string =
        "application/vnd.report.private.v1+json";
    public static getReports: (uri: string) => AxiosPromise<PrivateReportModel[]> =
        (uri: string) => {
            return this.get<PrivateReportModel[]>(uri, {
                headers: {
                    Accept: ReportsApi.REPORTS_PRIVATE_ACCEPT_HEADER,
                },
            });
        };

    public static getReport: (uri: string) => AxiosPromise<PrivateReportModel> =
        (uri: string) => {
            return this.get<PrivateReportModel>(uri, {
                headers: {
                    Accept: ReportsApi.REPORTS_PRIVATE_ACCEPT_HEADER,
                },
            });
        };
}

export default ReportsApi;