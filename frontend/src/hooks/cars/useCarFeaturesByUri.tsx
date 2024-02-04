import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQueries } from "@tanstack/react-query";
import CarFeaturesService from "@/services/CarFeaturesService";
import { useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";

const useCarFeaturesByUri = (carFeaturesUri: string[]) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const queries = useQueries({
    queries: carFeaturesUri.map((uri) => ({
      queryKey: ["carFeature", uri],
      queryFn: async () => {
        return await CarFeaturesService.getCarFeatureByUri(uri);
      },
    })),
    combine: (queryResult) => ({
      isLoading: queryResult.some((result) => result.isLoading),
      carFeatures: queryResult
        .map((result) => result.data)
        .filter((carFeature) => !!carFeature)
        .map((carFeature) => carFeature!),
      isError: queryResult.some((result) => result.isError),
      error: queryResult.find((result) => result.isError)?.error,
    }),
  });

  const { isError, error } = queries;

  useEffect(() => {
    if (isError && error) {
      const title = t("car_features.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "car_features.error.default",
      };
      onQueryError({
        error,
        title,
        customMessages,
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return queries;
};

export default useCarFeaturesByUri;
