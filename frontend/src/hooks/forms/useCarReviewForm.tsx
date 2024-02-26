import CarModel from "@/models/CarModel";
import { useTranslation } from "react-i18next";
import useSuccessToast from "../toasts/useSuccessToast";
import useQueryError from "../errors/useQueryError";
import { SubmitHandlerReturnModel } from "./useForm";
import { ReviewFormSchemaType } from "@/forms/ReviewForm";
import CarService from "@/services/CarService";
import TripModel from "@/models/TripModel";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useReviewForm from "./useReviewForm";
import useCarReviewOptions from "../cars/useCarReviewOptions";
import useCarByUri from "../cars/useCarByUri";

interface CarReviewFormHookProps {
  car: CarModel;
  trip: TripModel;
  onSuccess?: () => void;
  onError?: (error: Error) => void;
}

const useCarReviewForm = ({
  car,
  trip,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: CarReviewFormHookProps) => {
  const { t } = useTranslation();
  const { invalidateCarState } = useCarByUri(car.selfUri);
  const showSuccessToast = useSuccessToast();
  const onQueryError = useQueryError();

  const onSubmit: SubmitHandlerReturnModel<ReviewFormSchemaType, void> = async (
    data: ReviewFormSchemaType
  ) => {
    return await CarService.createCarReview(car.reviewsUri, trip.tripId, data);
  };

  const onSuccess = () => {
    showSuccessToast({
      title: t("car_review.success.title"),
      message: t("car_review.success.message"),
      timeout: defaultToastTimeout,
    });
    invalidateCarState();
    onSuccessProp?.();
  };

  const onError = (error: Error) => {
    const title = t("car_review.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: t("car_review.error.default"),
    };
    onQueryError({ error, title, timeout, customMessages });
    onErrorProp?.(error);
  };

  return useReviewForm({
    useReviewOptions: useCarReviewOptions,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useCarReviewForm;
