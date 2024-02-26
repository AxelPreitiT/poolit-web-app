import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import CarBrandsService from "@/services/CarBrandsService";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";

const useCarBrandByUri = (uri?: string) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["carBrand", uri],
    queryFn: async () => {
      if (!uri) {
        return undefined;
      }
      return await CarBrandsService.getCarBrandById(uri);
    },
    retry: false,
    enabled: !!uri,
  });

  const { isLoading, isPending, isError, error, data: carBrand } = query;

  useEffect(() => {
    if (isError) {
      const title = t("car_brands.error.title_one");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "car_brands.error.default_one",
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
    carBrand,
  };
};

export default useCarBrandByUri;
