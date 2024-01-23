interface PrivateReportModel {
    description: string;
    dateTime: string;
    reporterUri: string;
    reportedUri: string;
    reportState: string;
    reportOption: string;
    relation: string;
    adminReason: string;
    tripUri: string;
    selfUri: string;
}

export default PrivateReportModel;