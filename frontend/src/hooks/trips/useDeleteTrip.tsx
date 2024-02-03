import { useMutation } from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useTranslation } from "react-i18next";
import tripsService from "@/services/TripsService.ts";
import { parseTemplate } from "url-template";
import useTripsByUri from "./useTripsByUri";

const useDeleteTrip = (uri: string, id: number) => {
  const onQueryError = useQueryError();
  const { t } = useTranslation();
  const { invalidateTripsState } = useTripsByUri();

  const mutation = useMutation({
    mutationFn: async () => {
      const newUri = parseTemplate(uri as string).expand({
        tripId: id,
      });
      const ans = await tripsService.postDeleteTrip(newUri as string);
      await invalidateTripsState();
      return ans;
    },
    onError: (error: Error) => {
      onQueryError({
        error,
        title: t("trip.error_title_delete"),
        timeout: defaultToastTimeout,
      });
    },
    onSuccess: () => null,
  });

  const onSubmit = () => {
    mutation.mutate();
  };

  return { onSubmit, ...mutation };
};

export default useDeleteTrip;
