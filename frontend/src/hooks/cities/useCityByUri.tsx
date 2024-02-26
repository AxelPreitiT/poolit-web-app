import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { useQuery } from "@tanstack/react-query";
import CityService from "@/services/CityService.ts";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useEffect } from "react";

const useCityByUri = (uri?: string) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["city", uri],
    queryFn: async () => {
      if (!uri) {
        return undefined;
      }
      return await CityService.getCityByUri(uri);
    },
    retry: false,
    enabled: !!uri,
  });

  const { isError, error, data, isLoading, isPending } = query;

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("city.error.unique"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    isLoading: isLoading || isPending,
    city: data,
  };
};

export default useCityByUri;
