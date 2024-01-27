import {useMutation} from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useTranslation} from "react-i18next";
import PassangerService from "@/services/PassangerService.ts";

const useAcceptPassangerByUri = () => {
    const onQueryError = useQueryError();
    const { t } = useTranslation();

    const mutation = useMutation({
        mutationFn: async (uri: string) => {
            return await PassangerService.patchAcceptPassangersByUri(uri)
        },
        onError: (error: Error) => {
            onQueryError({
                error,
                title: t("passangers.error.title"),
                timeout: defaultToastTimeout,
            });
        },
        onSuccess: () => {
            console.log("passenger accepted")
        }
    })

    const onSubmit = (uri: string) => {
        mutation.mutate(uri)
    }

    return {onSubmit, ...mutation}

}

export default useAcceptPassangerByUri;