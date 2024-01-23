import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import reviewsService from "@/services/ReviewsService.ts";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";

const useUserReviewsByUri = (uri: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();

    const query = useQuery({
        queryKey: ["userReviews", uri],
        queryFn: async () => {
            return await reviewsService.getReviewsByUri(uri);
        },
        enabled: true,
        retry: false,
    });

    const { isLoading, isPending, isError, error, data } = query;

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
        reviews: data,
    }
}

export default useUserReviewsByUri;