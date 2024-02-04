import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import {parseTemplate} from "url-template";
import reviewsService from "@/services/ReviewsService.ts";
import {useQuery} from "@tanstack/react-query";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {useEffect} from "react";

const useDriverReviewsById = (id?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();
    const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();

    const query = useQuery({
        queryKey: ["user-reviews", id],
        queryFn: async () => {
            if (!id || !discovery?.driverReviewsUriTemplate) {
                return undefined;
            }
            const uri = parseTemplate(discovery.driverReviewsUriTemplate).expand({
                userId: id,
            });
            return await reviewsService.getReviewsByUri(uri);
        },
        retry: false,
        enabled: !isLoadingDiscovery && !!discovery?.driverReviewsUriTemplate && !!id,
    });

    const { isError, error, data, isLoading, isPending } = query;

    useEffect(() => {
        if (isError) {
            onQueryError({
                error,
                title: t("reviews.error.title_all"),
                timeout: defaultToastTimeout,
            });
        }
    }, [isError, error, onQueryError, t]);

    return {
        ...query,
        isLoading: isLoading || isPending,
        data: data
    };
}

export default useDriverReviewsById;