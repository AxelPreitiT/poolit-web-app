import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import CarService from "@/services/CarService";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";

const useCarReviewsByUri = (uri?: string) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["carReviews", uri],
    queryFn: async () => {
      if (!uri) {
        return undefined;
      }
      return await CarService.getCarReviews(uri);
    },
    retry: false,
    enabled: !!uri,
  });

  const { isLoading, isPending, isError, error, data: carReviews } = query;

  useEffect(() => {
    if (isError) {
      const title = t("car_reviews.error.title");
      const timeout = defaultToastTimeout;
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "car_reviews.error.default",
      };
      onQueryError({
        error: error,
        title,
        customMessages,
        timeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    isLoading: isLoading || isPending,
    carReviews,
  };
};

export default useCarReviewsByUri;
