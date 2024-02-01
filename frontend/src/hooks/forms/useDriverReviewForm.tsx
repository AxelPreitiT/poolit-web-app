import TripModel from "@/models/TripModel";
import UserPublicModel from "@/models/UserPublicModel";
import { useTranslation } from "react-i18next";
import useSuccessToast from "../toasts/useSuccessToast";
import useQueryError from "../errors/useQueryError";
import { SubmitHandlerReturnModel } from "./useForm";
import { ReviewFormSchemaType } from "@/forms/ReviewForm";
import DriverReviewsService from "@/services/DriverReviewsService";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useReviewForm from "./useReviewForm";
import useDriverReviewOptions from "../driver/useDriverReviewOptions";

interface DriverReviewFormHookProps {
  driver: UserPublicModel;
  trip: TripModel;
  onSuccess?: () => void;
  onError?: (error: Error) => void;
}

const useDriverReviewForm = ({
  driver,
  trip,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: DriverReviewFormHookProps) => {
  const { t } = useTranslation();
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();
  const { discovery, isError: isDiscoveryError } = useDiscovery();

  const onSubmit: SubmitHandlerReturnModel<ReviewFormSchemaType, void> = async (
    data: ReviewFormSchemaType
  ) => {
    if (!discovery || isDiscoveryError) {
      throw new DiscoveryMissingError();
    }
    return await DriverReviewsService.createReview(
      discovery.driverReviewsUriTemplate,
      trip.tripId,
      driver.userId,
      data
    );
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("driver_review.success.title"),
      message: t("driver_review.success.message"),
      timeout: defaultToastTimeout,
    });
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("driver_review.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: t("driver_review.error.default"),
    };
    onQueryError({ error, title, timeout, customMessages });
    onErrorProp?.(error);
  };

  return useReviewForm({
    useReviewOptions: useDriverReviewOptions,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useDriverReviewForm;
