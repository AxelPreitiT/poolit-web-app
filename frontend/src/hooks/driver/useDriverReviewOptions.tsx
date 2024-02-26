import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import DriverReviewsService from "@/services/DriverReviewsService";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { useQuery } from "@tanstack/react-query";

const useDriverReviewOptions = (rating: number) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["driverReviewOptions", rating],
    queryFn: async () => {
      return await DriverReviewsService.getDriverReviewOptionsByRating(rating);
    },
    retry: false,
  });

  const { isError, error, data: driverReviewOptions } = query;

  useEffect(() => {
    if (isError) {
      const title = t("driver_review_options.error.title");
      const timeout = defaultToastTimeout;
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: t(
          "driver_review_options.error.default"
        ),
      };
      onQueryError({ error, title, timeout, customMessages });
    }
  }, [isError, error, t, onQueryError]);

  return {
    ...query,
    driverReviewOptions,
  };
};

export default useDriverReviewOptions;
