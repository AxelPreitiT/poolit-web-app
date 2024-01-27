import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import useDiscovery from "../discovery/useDiscovery";
import { useQuery } from "@tanstack/react-query";
import CarBrandsService from "@/services/CarBrandsService";
import { useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";

const useCarBrands = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const { discovery, isLoading: isDiscoveryLoading } = useDiscovery();

  const query = useQuery({
    queryKey: ["carBrands"],
    queryFn: async () => {
      if (!discovery?.carBrandsUriTemplate) {
        return [];
      }
      return await CarBrandsService.getCarBrands(
        discovery.carBrandsUriTemplate
      );
    },
    retry: false,
    enabled: !isDiscoveryLoading && !!discovery?.carBrandsUriTemplate,
  });

  const { isLoading, isPending, isError, error, data: carBrands } = query;

  useEffect(() => {
    if (isError) {
      const title = t("car_brands.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "car_brands.error.default",
      };
      onQueryError({
        error: error,
        title,
        customMessages,
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    isLoading: isLoading || isPending,
    carBrands,
  };
};

export default useCarBrands;
