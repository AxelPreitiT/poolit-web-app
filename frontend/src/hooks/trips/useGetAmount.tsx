import {useQuery} from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import TripsService from "@/services/TripsService.ts";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import UnknownResponseError from "@/errors/UnknownResponseError.ts";
import tripEarningModel from "@/models/tripEarningModel.ts";

const useGetEarning = (tripUri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const {
        isLoading,
        isError,
        data: earning,
        error,
        isPending,
    } = useQuery({
        queryKey: ["tripp", tripUri],
        queryFn: async (): Promise<tripEarningModel> => {
            return await TripsService.getAmountByUri(tripUri as string);
        },
        retry: false,
        enabled: !!tripUri,
    });

    useEffect(() => {
        if (isError) {
            const title = t("trip_detail.error.title");
            const customMessages = {
                [UnknownResponseError.ERROR_ID]: "trip_detail.error.default",
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
        earning,
        isError,
    };
};

export default useGetEarning;
