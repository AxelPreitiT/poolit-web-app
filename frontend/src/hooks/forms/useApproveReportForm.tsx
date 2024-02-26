import PrivateReportModel from "@/models/PrivateReportModel";
import { useTranslation } from "react-i18next";
import useSuccessToast from "../toasts/useSuccessToast";
import useQueryError from "../errors/useQueryError";
import { SubmitHandlerReturnModel } from "./useForm";
import { DecideReportFormSchemaType } from "@/forms/DecideReportForm";
import ReportService from "@/services/ReportService";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useDecideReportForm from "./useDecideReportForm";

interface ApproveReportFormHookProps {
  report: PrivateReportModel;
  onSuccess?: () => void;
  onError?: (error: Error) => void;
}

const useApproveReportForm = ({
  report,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: ApproveReportFormHookProps) => {
  const { t } = useTranslation();
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();

  const onSubmit: SubmitHandlerReturnModel<
    DecideReportFormSchemaType,
    void
  > = async (data: DecideReportFormSchemaType) => {
    return await ReportService.approveReport(report.selfUri, data);
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("approve_report.success.title"),
      message: t("approve_report.success.message"),
      timeout: defaultToastTimeout,
    });
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("approve_report.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: t("approve_report.error.default"),
    };
    onQueryError({ error, title, timeout, customMessages });
    onErrorProp?.(error);
  };

  return useDecideReportForm({
    report,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useApproveReportForm;
