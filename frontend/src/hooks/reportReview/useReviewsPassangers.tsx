import {useTranslation} from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import {useQuery} from "@tanstack/react-query";
import {useEffect} from "react";
import {defaultToastTimeout} from "@/components/toasts/ToastProps.ts";
import {parseTemplate} from "url-template";
import PassangerService from "@/services/PassangerService.ts";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";
import passangerModel from "@/models/PassangerModel.ts";

const useReviewsReportPassangers = (passanger: passangerModel, reporting?:boolean) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();
    const {data:currentUser} = useCurrentUser();

    const query = useQuery({
        queryKey: ["passangersReviews", passanger],
        queryFn: async () => {
            if(reporting){
                const reportUri = parseTemplate(passanger.passengerReportsForTripUriTemplate as string).expand({
                    userId: currentUser?.userId as number,
                });
                return await PassangerService.getReport(reportUri);
            }else{
                const reviewUri = parseTemplate(passanger.passengerReviewsForTripUriTemplate as string).expand({
                    userId: currentUser?.userId as number,
                });
                return await PassangerService.getReview(reviewUri);
            }
            },
        retry: true
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

export default useReviewsReportPassangers;