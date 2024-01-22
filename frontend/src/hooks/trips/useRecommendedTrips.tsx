import UserPrivateModel from "@/models/UserPrivateModel";
import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import TripsService from "@/services/TripsService";
import TripModel from "@/models/TripModel";
import { useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";

const useRecommendedTrips = (user?: UserPrivateModel) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["recommendedTrips", user?.recommendedTripsUri],
    queryFn: async (): Promise<TripModel[]> => {
      if (!user?.recommendedTripsUri) {
        return [];
      }
      return await TripsService.getRecommendedTrips(user?.recommendedTripsUri);
    },
    retry: false,
    enabled: !!user,
  });

  const {
    isLoading,
    isPending,
    isError,
    error,
    data: recommendedTrips,
  } = query;

  useEffect(() => {
    if (isError) {
      const title = t("recommended_trips.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "recommended_trips.error.default",
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
    ...query,
    isLoading: isLoading || isPending,
    recommendedTrips,
  };
};

export default useRecommendedTrips;
