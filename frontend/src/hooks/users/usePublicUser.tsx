import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import userService from "@/services/UserService.ts";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";

const usePublicUser = (uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const query = useQuery({
        queryKey: ["public-user", uri],
        queryFn: async () => {
            if (!uri) {
                return undefined;
            }
            return await userService.getUserById(uri);
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
        driver: data,
    };
}

export default usePublicUser;