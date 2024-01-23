import {useTranslation} from "react-i18next";
import TripsService from "@/services/TripsService.ts";
import TripModel from "@/models/TripModel.ts";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import UnknownResponseError from "@/errors/UnknownResponseError.ts";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import useQueryError from "@/hooks/errors/useQueryError.tsx";

const useTripsByUri = (tripsUri: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const {
        isLoading,
        isError,
        data: trips,
        error,
        isPending,
    } = useQuery({
        queryKey: ["trips", tripsUri],
        queryFn: async ({ queryKey }): Promise<TripModel[]> => {
            const [, tripsUri] = queryKey;
            return await TripsService.getTripsByUri(tripsUri);
        },
        retry: false,
    });

    useEffect(() => {
        if (isError) {
            const title = t("created_trips.error.all_title");
            const customMessages = {
                [UnknownResponseError.ERROR_ID]: "created_trips.error.all_default",
            };
            onQueryError({
                error: error,
                timeout: defaultToastTimeout,
                title,
                customMessages,
            });
        }
    }, [isError, error, onQueryError, t]);

    return {
        isLoading: isLoading || isPending,
        trips,
    };
}

export default useTripsByUri;