import ReportRelation from "@/enums/ReportRelation";
import TripModel from "@/models/TripModel";
import UserPublicModel from "@/models/UserPublicModel";
import { useTranslation } from "react-i18next";
import useSuccessToast from "../toasts/useSuccessToast";
import useQueryError from "../errors/useQueryError";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import {
  ReportForm,
  ReportFormSchema,
  ReportFormSchemaType,
} from "@/forms/ReportForm";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";
import ReportService from "@/services/ReportService";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";

interface ReportFormHookProps {
  trip: TripModel;
  relation: ReportRelation;
  reported: UserPublicModel;
  onSuccess?: () => void;
  onError?: (error: Error) => void;
}

const useReportForm = ({
  trip,
  relation,
  reported,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: ReportFormHookProps) => {
  const { t } = useTranslation();
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();
  const { discovery, isError: isDiscoveryError } = useDiscovery();

  const onSubmit: SubmitHandlerReturnModel<ReportFormSchemaType, void> = async (
    data: ReportFormSchemaType
  ) => {
    if (!discovery || isDiscoveryError) {
      throw new DiscoveryMissingError();
    }
    return await ReportService.createReport(
      discovery.reportsUriTemplate,
      trip.tripId,
      reported.userId,
      relation,
      data
    );
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("report.success.title"),
      message: t("report.success.message"),
      timeout: defaultToastTimeout,
    });
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("report.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: t("report.error.default"),
    };
    onQueryError({ error, title, timeout, customMessages });
    onErrorProp?.(error);
  };

  return useForm({
    form: ReportForm,
    formSchema: ReportFormSchema,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useReportForm;
