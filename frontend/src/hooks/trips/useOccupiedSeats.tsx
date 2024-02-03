import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import occupiedSeatsModel from "@/models/occupiedSeatsModel.ts";
import tripsService from "@/services/TripsService.ts";
import { parseTemplate } from "url-template";

const useOccupiedSeats = (
  startDateTime?: string,
  endDateTime?: string,
  uri?: string
) => {
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
    queryKey: ["OccupiedSeats", uri],
    queryFn: async (): Promise<occupiedSeatsModel> => {
      const newUri = parseTemplate(uri as string).expand({
        userId: null,
        startDateTime: startDateTime as string,
        endDateTime: endDateTime as string,
        passengerState: null,
      });
      return await tripsService.getOccupiedSeats(newUri as string);
    },
    retry: false,
    enabled: !!uri && !!startDateTime && !!endDateTime,
  });

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("passanger.error.title"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  const invalidateOccupiedSeats = () => {
    queryClient.invalidateQueries({ queryKey: ["OccupiedSeats"] });
  };

  return {
    isLoading: isLoading || isPending,
    data: data,
    invalidateOccupiedSeats,
  };
};

export default useOccupiedSeats;
