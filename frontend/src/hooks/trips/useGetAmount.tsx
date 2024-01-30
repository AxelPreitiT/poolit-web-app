import {useQuery} from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import TripsService from "@/services/TripsService.ts";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import UnknownResponseError from "@/errors/UnknownResponseError.ts";
import tripEarningModel from "@/models/tripEarningModel.ts";
import {useParams, useSearchParams} from "react-router-dom";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import {parseTemplate} from "url-template";

const useGetEarning = () => {
    const { t } = useTranslation();
    const param = useParams();
    const id = param.tripId;
    const onQueryError = useQueryError();
    const { isLoading: isLoadingDiscovery, discovery } = useDiscovery()
    const [params] = useSearchParams();

    const startDateTime = params.get("startDateTime") || "";
    const endDateTime = params.get("endDateTime") || "";

    const {
        isLoading,
        isError,
        data: earning,
        error,
        isPending,
    } = useQuery({
        queryKey: ["getEargning"],
        queryFn: async (): Promise<tripEarningModel | undefined> => {
            const uri = parseTemplate(discovery?.tripsUriTemplate as string).expand({
                tripId: id as string,
                startDateTime : startDateTime,
                endDateTime : endDateTime
            });

            return await TripsService.getAmountByUri(uri as string);
        },
        retry: false,
        enabled: !isLoadingDiscovery,
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
