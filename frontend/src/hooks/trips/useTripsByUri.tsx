import {useTranslation} from "react-i18next";
import TripsService from "@/services/TripsService.ts";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import UnknownResponseError from "@/errors/UnknownResponseError.ts";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import PaginationModel from "@/models/PaginationModel.tsx";
import TripModel from "@/models/TripModel.ts";

const useTripsByUri = (tripsUri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const {
        isLoading,
        isError,
        data: data,
        error,
        isPending,
    } = useQuery({
        queryKey: ["trips", tripsUri],
        queryFn: async ({ queryKey }): Promise<PaginationModel<TripModel>> => {
            const [, tripsUri] = queryKey;
            return await TripsService.getTripsByUri(tripsUri as string);
        },
        retry: false,
        enabled: !!tripsUri,
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
        data,
    };
}

export default useTripsByUri;