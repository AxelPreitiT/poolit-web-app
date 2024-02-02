import { useMutation } from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useTranslation } from "react-i18next";
import tripModel from "@/models/TripModel.ts";
import tripsService from "@/services/TripsService.ts";
import joinTripModel from "@/models/JoinTripModel.ts";
import { parseTemplate } from "url-template";
import useAuthentication from "../auth/useAuthentication";
import JoinTripUnauthenticatedError from "@/errors/JoinTripUnauthenticatedError";
import useTrip from "./useTrip";
import useRolePassanger from "../passanger/useRolePassanger";
import useSuccessToast from "../toasts/useSuccessToast";
import { getIsoDate, parseIsoDate } from "@/utils/date/isoDate";

const useJoinTrip = (
  trip: tripModel,
  startDateTime: string,
  endDateTime: string
) => {
  const onQueryError = useQueryError();
  const { t } = useTranslation();
  const { invalidateTripDetails } = useTrip();
  const { invalidatePassangerRole } = useRolePassanger();
  const showSuccessToast = useSuccessToast();
  const isAuthenticated = useAuthentication();

  const invalidateTripState = () => {
    invalidateTripDetails();
    invalidatePassangerRole();
  };

  const mutation = useMutation({
    mutationFn: async () => {
      if (!isAuthenticated) {
        throw new JoinTripUnauthenticatedError();
      }
      const startDate = getIsoDate(parseIsoDate(startDateTime));
      const endDate = getIsoDate(parseIsoDate(endDateTime));
      const data: joinTripModel = {
        startDate,
        endDate: endDate == startDate ? undefined : endDate,
      };
      const uri = parseTemplate(trip?.passengersUriTemplate as string).expand({
        userId: null,
        startDateTime: null,
        endDateTime: null,
        passengerState: null,
      });
      return await tripsService.postJoinTrip(uri as string, data);
    },
    onError: (error: Error) => {
      onQueryError({
        error,
        title: t("trip.error_title_join"),
        timeout: defaultToastTimeout,
      });
    },
    onSuccess: () => {
      showSuccessToast({
        timeout: defaultToastTimeout,
        title: t("trip.success_title_join"),
        message: t("trip.success_message_join"),
      });
      invalidateTripState();
    },
  });

  const onSubmit = () => {
    mutation.mutate();
  };

  return { onSubmit, invalidateTripState, ...mutation };
};

export default useJoinTrip;
