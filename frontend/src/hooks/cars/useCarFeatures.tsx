import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import CarFeaturesService from "@/services/CarFeaturesService";
import { useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import useDiscovery from "../discovery/useDiscovery";

const useCarFeatures = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();

  const query = useQuery({
    queryKey: ["carFeatures"],
    queryFn: async () => {
      if (!discovery?.carFeaturesUriTemplate) {
        return [];
      }
      return await CarFeaturesService.getCarFeatures(
        discovery.carFeaturesUriTemplate
      );
    },
    retry: false,
    enabled: !isLoadingDiscovery && !!discovery?.carFeaturesUriTemplate,
  });

  const { isLoading, isPending, isError, error, data: carFeatures } = query;

  useEffect(() => {
    if (isError) {
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

  return {
    ...query,
    isLoading: isLoading || isPending,
    carFeatures,
  };
};

export default useCarFeatures;
