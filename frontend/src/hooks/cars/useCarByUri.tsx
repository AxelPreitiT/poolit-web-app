import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import CarService from "@/services/CarService.ts";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import UnknownResponseError from "@/errors/UnknownResponseError.ts";

const useCarByUri = (uri?: string) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const onQueryError = useQueryError();

  const {
    isLoading,
    isError,
    data: car,
    error,
    isPending,
  } = useQuery({
    queryKey: ["carUri", uri],
    queryFn: async () => {
      if (!uri) {
        return undefined;
      }
      return await CarService.getCarByUri(uri);
    },
    retry: false,
    enabled: !!uri,
  });

  useEffect(() => {
    if (isError) {
      const title = t("car.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "car.error.your",
      };
      onQueryError({
        error: error,
        timeout: defaultToastTimeout,
        title,
        customMessages,
      });
    }
  }, [isError, error, onQueryError, t]);

  const invalidateCarState = () => {
    queryClient.invalidateQueries({
      queryKey: ["carUri", uri],
    });
  };

  return {
    isLoading: isLoading || isPending,
    car,
    isError,
    error,
    invalidateCarState,
  };
};

export default useCarByUri;
