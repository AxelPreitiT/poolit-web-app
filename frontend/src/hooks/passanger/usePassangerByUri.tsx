import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import passangerService from "@/services/PassangerService.ts";
import PaginationModel from "@/models/PaginationModel.ts";
import PassangerModel from "@/models/PassangerModel.ts";

const queryKey = "passangersPagination";

const usePassangerByUri = (uri?: string) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const onQueryError = useQueryError();

  const {
    isLoading,
    isError,
    data: data,
    error,
    isPending,
  } = useQuery({
    queryKey: [queryKey, uri],
    queryFn: async (): Promise<PaginationModel<PassangerModel>> => {
      return await passangerService.getPassangersTripsByUri(uri as string);
    },
    retry: false,
    enabled: !!uri,
  });

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("passangers.error.title"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  const invalidatePassangersState = () => {
    queryClient.invalidateQueries({
      queryKey: [queryKey],
    });
  };

  return {
    isLoading: isLoading || isPending,
    data: data,
    invalidatePassangersState,
  };
};

export default usePassangerByUri;
