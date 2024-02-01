import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { parseTemplate } from "url-template";
import PassangerService from "@/services/PassangerService.ts";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import TripModel from "@/models/TripModel.ts";

const useReviewsReportsDriver = (reporting: boolean, trip: TripModel) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const onQueryError = useQueryError();
  const { isLoading: isLoadingUser, data: currentUser } = useCurrentUser();

  const query = useQuery({
    queryKey: ["driversRR", trip.tripId, reporting],
    queryFn: async () => {
      if (reporting) {
        const parseUri = parseTemplate(
          trip.driverReportsUriTemplate as string
        ).expand({
          userId: currentUser?.userId as number,
        });
        return await PassangerService.getReport(parseUri);
      } else {
        const parseUri = parseTemplate(
          trip.driverReviewsUriTemplate as string
        ).expand({
          userId: currentUser?.userId as number,
        });
        return await PassangerService.getReview(parseUri);
      }
    },
    enabled: !!trip && !isLoadingUser,
    retry: false,
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

  const invalidateDriverReviewQuery = () => {
    queryClient.invalidateQueries({
      queryKey: ["driversRR", trip.tripId, false],
    });
  };

  const invalidateDriverReportQuery = () => {
    queryClient.invalidateQueries({
      queryKey: ["driversRR", trip.tripId, true],
    });
  };

  return {
    ...query,
    isLoading: isLoading || isPending,
    data: data,
    invalidateDriverReviewQuery,
    invalidateDriverReportQuery,
  };
};
export default useReviewsReportsDriver;
