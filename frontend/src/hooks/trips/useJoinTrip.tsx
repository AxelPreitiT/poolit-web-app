import {useMutation, useQueryClient} from "@tanstack/react-query";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useTranslation} from "react-i18next";
import tripModel from "@/models/TripModel.ts";
import {useSearchParams} from "react-router-dom";
import tripsService from "@/services/TripsService.ts";
import joinTripModel from "@/models/JoinTripModel.ts";
import {parseTemplate} from "url-template";

const useJoinTrip = (trip: tripModel) => {
    const onQueryError = useQueryError();
    const { t } = useTranslation();
    const [params] = useSearchParams();
    const startDate = params.get("startDate") || "";
    const endDate = params.get("endDate") || "";
    const queryClient = useQueryClient();

    const invalidateTripState = () => {
        queryClient.invalidateQueries({ queryKey: ['tripDetails'] });
        queryClient.invalidateQueries({ queryKey: ['rolePassanger'] });
    }

    const mutation = useMutation({
        mutationFn: async () => {
            const data: joinTripModel = {
                startDate: startDate ,
                endDate: endDate==startDate? undefined : endDate
            }
            const uri = parseTemplate(trip?.passengersUriTemplate as string).expand({
                userId: null,
                startDateTime: null,
                endDateTime: null,
                passengerState: null,
            });
            return await tripsService.postJoinTrip(uri as string, data)
        },
        onError: (error: Error) => {
            onQueryError({
                error,
                title: t("trips.error_title_join"),
                timeout: defaultToastTimeout,
            });
        },
        onSuccess: () => {
            invalidateTripState()
        }
    })

    const onSubmit = () => {
        mutation.mutate()
    }

    return {onSubmit, invalidateTripState, ...mutation}

}

export default useJoinTrip;