import {useMutation, useQueryClient} from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useTranslation} from "react-i18next";
import tripModel from "@/models/TripModel.ts";
import tripsService from "@/services/TripsService.ts";
import {parseTemplate} from "url-template";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";

const useDeleteTrip = (trip: tripModel) => {
    const onQueryError = useQueryError();
    const { t } = useTranslation();
    const queryClient = useQueryClient();

    const invalidatePassangersState = () => {
        queryClient.invalidateQueries({ queryKey: ['tripDetails'] });
    }

    const mutation = useMutation({
        mutationFn: async () => {
            const {discovery} = useDiscovery()
            const uri = parseTemplate(discovery?.tripsUriTemplate as string).expand({
                tripId: trip.tripId,
            });
            return await tripsService.postDeleteTrip(uri as string)
        },
        onError: (error: Error) => {
            onQueryError({
                error,
                title: t("trip.error_title_delete"),
                timeout: defaultToastTimeout,
            });
        },
        onSuccess: () => {
            invalidatePassangersState()
            console.log("passenger accepted")
        }
    })

    const onSubmit = () => {
        mutation.mutate()
    }

    return {onSubmit, invalidatePassangersState, ...mutation}

}

export default useDeleteTrip;