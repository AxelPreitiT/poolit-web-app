import { useQuery, useQueryClient } from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import TripsService from "@/services/TripsService";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import UnknownResponseError from "@/errors/UnknownResponseError";
import TripModel from "@/models/TripModel";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import { parseTemplate } from "url-template";
import { useParams, useSearchParams } from "react-router-dom";

const useTrip = () => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const param = useParams();
  const id = param.tripId;
  const onQueryError = useQueryError();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();
  const [params] = useSearchParams();

  const startDateTime = params.get("startDateTime") || "";
  const endDateTime = params.get("endDateTime") || "";

  const {
    isLoading,
    isError,
    data: trip,
    error,
    isPending,
  } = useQuery({
    queryKey: ["tripDetails", id],
    queryFn: async (): Promise<TripModel> => {
      const uri = parseTemplate(discovery?.tripsUriTemplate as string).expand({
        tripId: id as string,
        startDateTime: startDateTime,
        endDateTime: endDateTime,
      });

      return await TripsService.getTripById(uri);
    },
    retry: false,
    enabled: !isLoadingDiscovery,
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

  const invalidateTripDetails = () => {
    queryClient.invalidateQueries({
      queryKey: ["tripDetails", id],
    });
  };

  return {
    isLoading: isLoading || isPending,
    trip,
    isError,
    error,
    invalidateTripDetails,
  };
};

export default useTrip;
