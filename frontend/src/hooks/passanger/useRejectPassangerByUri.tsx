import { useMutation } from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useTranslation } from "react-i18next";
import PassangerService from "@/services/PassangerService.ts";
import usePassangerByUri from "./usePassangerByUri";

const useRejectPassangerByUri = () => {
  const onQueryError = useQueryError();
  const { t } = useTranslation();
  const { invalidatePassangersState } = usePassangerByUri();

  const mutation = useMutation({
    mutationFn: async (uri: string) => {
      return await PassangerService.patchRejectPassangersByUri(uri);
    },
    onError: (error: Error) => {
      onQueryError({
        error,
        title: t("passangers.error.title"),
        timeout: defaultToastTimeout,
      });
    },
    onSuccess: () => {
      invalidatePassangersState();
      console.log("passenger accepted");
    },
  });

  const onSubmit = (uri: string) => {
    mutation.mutate(uri);
  };

  return { onSubmit, invalidatePassangersState, ...mutation };
};

export default useRejectPassangerByUri;
