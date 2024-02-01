import {useMutation, useQueryClient} from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useTranslation} from "react-i18next";
import tripsService from "@/services/TripsService.ts";
import {parseTemplate} from "url-template";

const useDeleteTrip = (uri: string, id: number) => {
    const onQueryError = useQueryError();
    const { t } = useTranslation();
    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: async () => {
            const newUri = parseTemplate(uri as string).expand({
                tripId: id,
            });
            const ans = await tripsService.postDeleteTrip(newUri as string)
            await queryClient.invalidateQueries({ queryKey: ['trips'] })
            return ans
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

export default useDeleteTrip;