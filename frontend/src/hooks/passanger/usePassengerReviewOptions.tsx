import PassengerReviewsService from "@/services/PassengerReviewsService";
import { useQuery } from "@tanstack/react-query";
import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";

const usePassengerReviewOptions = (rating: number) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["passengerReviewOptions", rating],
    queryFn: async () => {
      return await PassengerReviewsService.getPassengerReviewOptionsByRating(
        rating
      );
    },
    retry: false,
  });

  const { isError, error, data: passengerReviewOptions } = query;

  useEffect(() => {
    if (isError) {
      const title = t("passenger_review_options.error.title");
      const timeout = defaultToastTimeout;
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: t(
          "passenger_review_options.error.default"
        ),
      };
      onQueryError({ error, title, timeout, customMessages });
    }
  }, [isError, error, t, onQueryError]);

  return {
    ...query,
    passengerReviewOptions,
  };
};

export default usePassengerReviewOptions;
