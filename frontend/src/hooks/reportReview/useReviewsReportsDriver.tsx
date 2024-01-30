import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {parseTemplate} from "url-template";
import PassangerService from "@/services/PassangerService.ts";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";
import tripModel from "@/models/TripModel.ts";

const useReviewsReportsDriver = (reporting:boolean, trip: tripModel) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();
    const {isLoading:isLoadingUser, data:currentUser} = useCurrentUser();

    const query = useQuery({
        queryKey: ["driversReviews", trip, reporting],
        queryFn: async () => {
            if (reporting){
                const parseUri = parseTemplate(trip.driverReportsUriTemplate as string).expand({
                    userId: currentUser?.userId as number,
                });
                return await PassangerService.getReport(parseUri);
            }else{
                const parseUri = parseTemplate(trip.driverReviewsUriTemplate as string).expand({
                    userId: currentUser?.userId as number,
                });
                return await PassangerService.getReview(parseUri);
            }
        },
        enabled: !!trip && !isLoadingUser,
        retry: false
    });

    const { isLoading, isPending, isError, error, data } = query;

    useEffect(() => {
        if (isError) {
            onQueryError({
                error,
                title: t("passanger.error.title"),
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
export default useReviewsReportsDriver;