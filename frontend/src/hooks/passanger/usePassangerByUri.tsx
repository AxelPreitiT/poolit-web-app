import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import passangerService from "@/services/PassangerService.ts";
import PaginationModel from "@/models/PaginationModel.ts";
import PassangerModel from "@/models/PassangerModel.ts";

const usePassangerByUri = (uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const {
        isLoading,
        isError,
        data: data,
        error,
        isPending,
    } = useQuery({
        queryKey: ["passangers", uri],
        queryFn: async ({ queryKey }): Promise<PaginationModel<PassangerModel>> => {
            const [, uri] = queryKey;
            return await passangerService.getPassangersTripsByUri(uri as string);
        },
        retry: false,
        enabled: !!uri,
    });

    useEffect(() => {
        if (isError) {
            onQueryError({
                error,
                title: t("passangers.error.title"),
                timeout: defaultToastTimeout,
            });
        }
    }, [isError, error, onQueryError, t]);

    return {
        isLoading: isLoading || isPending,
        data: data,
    }

}

export default usePassangerByUri;