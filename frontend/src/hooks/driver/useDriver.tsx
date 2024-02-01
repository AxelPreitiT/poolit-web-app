import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import userService from "@/services/UserService.ts";
import { useQuery } from "@tanstack/react-query";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useEffect } from "react";


const useDriverByUri = (uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const query = useQuery({
        queryKey: ["driverUser", uri],
        queryFn: async () => {
            return await userService.getDriverUser(uri as string);
        },
        retry: false,
        enabled: !!uri,
    });

    const { isError, error, data, isLoading, isPending } = query;

    useEffect(() => {
        if (isError) {
            onQueryError({
                error,
                title: t("profile.error.title"),
                timeout: defaultToastTimeout,
            });
        }
    }, [isError, error, onQueryError, t]);

    return {
        ...query,
        isLoading: isLoading || isPending,
        data: data,
    };
};

export default useDriverByUri;
