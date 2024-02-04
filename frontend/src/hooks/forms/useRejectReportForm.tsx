import PrivateReportModel from "@/models/PrivateReportModel";
import { useTranslation } from "react-i18next";
import useSuccessToast from "../toasts/useSuccessToast";
import useQueryError from "../errors/useQueryError";
import { SubmitHandlerReturnModel } from "./useForm";
import { DecideReportFormSchemaType } from "@/forms/DecideReportForm";
import ReportService from "@/services/ReportService";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useDecideReportForm from "./useDecideReportForm";

interface RejectReportFormHookProps {
  report: PrivateReportModel;
  onSuccess?: () => void;
  onError?: (error: Error) => void;
}

const useRejectReportForm = ({
  report,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: RejectReportFormHookProps) => {
  const { t } = useTranslation();
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();

  const onSubmit: SubmitHandlerReturnModel<
    DecideReportFormSchemaType,
    void
  > = async (data: DecideReportFormSchemaType) => {
    return await ReportService.rejectReport(report.selfUri, data);
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("reject_report.success.title"),
      message: t("reject_report.success.message"),
    });
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("reject_report.error.title");
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: t("reject_report.error.default"),
    };
    onQueryError({ error, title, customMessages });
    onErrorProp?.(error);
  };

  return useDecideReportForm({
    report,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useRejectReportForm;
