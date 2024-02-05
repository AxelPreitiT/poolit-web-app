import { useMutation } from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useTranslation } from "react-i18next";
import PassangerService from "@/services/PassangerService.ts";
import usePassangerByUri from "./usePassangerByUri";
import useTrip from "../trips/useTrip";
import useOccupiedSeats from "../trips/useOccupiedSeats";
import {useQueryClient} from "@tanstack/react-query";

const useAcceptPassangerByUri = () => {
  const onQueryError = useQueryError();
  const { t } = useTranslation();
  const { invalidatePassangersState } = usePassangerByUri();
  const { invalidateTripDetails } = useTrip();
  const { invalidateOccupiedSeats } = useOccupiedSeats();
  //POSIBLE SOLUCION A LA ACTUALIZACION DE ACUMULADO
  const queryClient = useQueryClient();


  const mutation = useMutation({
    mutationFn: async (uri: string) => {
      return await PassangerService.patchAcceptPassangersByUri(uri);
    },
    onError: (error: Error) => {
      onQueryError({
        error,
        title: t("passanger.error.title"),
        timeout: defaultToastTimeout,
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: ["getEargning"],});
      invalidatePassangersState();
      invalidateOccupiedSeats();
      invalidateTripDetails();
    },
  });

  const onSubmit = (uri: string) => {
    mutation.mutate(uri);
  };

  return { onSubmit, invalidatePassangersState, ...mutation };
};

export default useAcceptPassangerByUri;
