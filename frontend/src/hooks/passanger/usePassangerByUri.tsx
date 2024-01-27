import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {useQuery} from "@tanstack/react-query";
import passangerService from "@/services/PassangerService.ts";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";

const usePassangerByUri = (uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const query = useQuery({
        queryKey: ["passangers", uri],
        queryFn: async () => {
            return await passangerService.getPassangersTripsByUri(uri as string);
        },
        enabled: !!uri
    });

    const { isLoading, isPending, isError, error, data } = query;

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
        ...query,
        isLoading: isLoading || isPending,
        data: data,
    }

}

export default usePassangerByUri;