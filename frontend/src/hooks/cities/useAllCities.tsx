import CityService from "@/services/CityService";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";
import useDiscovery from "../discovery/useDiscovery";

const useAllCities = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();

  const query = useQuery({
    queryKey: ["cities"],
    queryFn: async () => {
      if (!discovery?.citiesUriTemplate) {
        return [];
      }
      return await CityService.getAllCities(discovery.citiesUriTemplate);
    },
    retry: false,
    enabled: !isLoadingDiscovery && !!discovery?.citiesUriTemplate,
  });

  const { isError, error, data, isLoading, isPending } = query;

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("city.error.title"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    isLoading: isLoading || isPending,
    cities: data,
  };
};

export default useAllCities;
