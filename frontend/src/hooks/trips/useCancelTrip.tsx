import {useMutation} from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useTranslation} from "react-i18next";
import passangerService from "@/services/PassangerService.ts";

const usecancelTrip = (uri?: string) => {
    const onQueryError = useQueryError();
    const { t } = useTranslation();

    const mutation = useMutation({
        mutationFn: async () => {
            return await passangerService.deleteCancelTrip(uri as string)
        },
        onError: (error: Error) => {
            onQueryError({
                error,
                title: t("trip.error_title_delete"),
                timeout: defaultToastTimeout,
            });
        },
        onSuccess: () => {
            console.log("trip delete")
        },
    })

    const onSubmit = () => {
        mutation.mutate()
    }

    return {onSubmit, ...mutation}

}

export default usecancelTrip;