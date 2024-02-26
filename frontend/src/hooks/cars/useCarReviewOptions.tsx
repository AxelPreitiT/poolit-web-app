import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import CarService from "@/services/CarService";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";

const useCarReviewOptions = (rating: number) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["carReviewOptions", rating],
    queryFn: async () => {
      return await CarService.getCarReviewOptionsByRating(rating);
    },
    retry: false,
  });

  const { isError, error, data: carReviewOptions } = query;

  useEffect(() => {
    if (isError) {
      const title = t("car_review_options.error.title");
      const timeout = defaultToastTimeout;
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: t("car_review_options.error.default"),
      };
      onQueryError({ error, title, timeout, customMessages });
    }
  }, [isError, error, t, onQueryError]);

  return {
    ...query,
    carReviewOptions,
  };
};

export default useCarReviewOptions;
