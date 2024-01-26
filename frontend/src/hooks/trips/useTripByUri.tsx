import { useQuery } from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import TripsService from "@/services/TripsService";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import UnknownResponseError from "@/errors/UnknownResponseError";
import TripModel from "@/models/TripModel";

const useTripByUri = (tripUri?: string) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const {
    isLoading,
    isError,
    data: trip,
    error,
    isPending,
  } = useQuery({
    queryKey: ["trip", tripUri],
    queryFn: async (): Promise<TripModel> => {

      return await TripsService.getTripById(tripUri as string);
    },
    retry: false,
    enabled: !!tripUri,
  });

  useEffect(() => {
    if (isError) {
      const title = t("trip_detail.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "trip_detail.error.default",
      };
      onQueryError({
        error: error,
        timeout: defaultToastTimeout,
        title,
        customMessages,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    isLoading: isLoading || isPending,
    trip,
    isError,
    error,
  };
};

export default useTripByUri;
