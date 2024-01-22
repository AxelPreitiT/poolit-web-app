import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useQuery} from "@tanstack/react-query";
import PassangerService from "@/services/PassangerService.ts";
import {parseTemplate} from "url-template";

const usePassangers = (uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();


    const query = useQuery({
        queryKey: ["passangers"],
        queryFn: async () => {
            if (!uri) {
                return undefined;
            }
            const uriAllPassangers = parseTemplate(uri).expand({
                userId: null,
            });
            return await PassangerService.getPassangersTrips(uriAllPassangers);
        },
        retry: false,
        enabled: !!uri,
    });

    const { isError, error, data, isLoading, isPending } = query;

    useEffect(() => {
        if (isError) {
            onQueryError({
                error,
                title: t("city.error.title"),
                timeout: defaultToastTimeout,
            });
        }
    }, [isError, error, onQueryError, t]);

    return {
        ...query,
        isLoading: isLoading || isPending,
        passangers: data,
    };
};

export default usePassangers;