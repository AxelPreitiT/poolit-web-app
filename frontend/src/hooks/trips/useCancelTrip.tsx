import { useMutation } from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useTranslation } from "react-i18next";
import passangerService from "@/services/PassangerService.ts";
import useTripsByUri from "./useTripsByUri";

const useCancelTrip = (uri?: string) => {
  const onQueryError = useQueryError();
  const { t } = useTranslation();
  const { invalidateTripsState } = useTripsByUri();

  const mutation = useMutation({
    mutationFn: async () => {
      const ans = await passangerService.deleteCancelTrip(uri as string);
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

export default useCancelTrip;
