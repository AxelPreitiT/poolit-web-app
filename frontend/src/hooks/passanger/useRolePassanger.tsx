import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useQuery} from "@tanstack/react-query";
import PassangerService from "@/services/PassangerService.ts";
import {parseTemplate} from "url-template";
import UserPrivateModel from "@/models/UserPrivateModel.ts";

const useRolePassanger = (currentUser?: UserPrivateModel, uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const query = useQuery({
        queryKey: ["rolePassanger"],
        queryFn: async () => {
            if (!uri || currentUser === undefined) {
                return undefined;
            }
            const params = currentUser.selfUri.split("/");
            const id = params[params.length - 1];
            const uriAllPassangers = parseTemplate(uri).expand({
                userId: id  ,
            });
            return await PassangerService.getPassangerRole(uriAllPassangers);
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
        isError,
        isLoading: isLoading || isPending,
        passangersRole: data,
    };
};

export default useRolePassanger;