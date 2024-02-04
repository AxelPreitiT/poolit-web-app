import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { parseTemplate } from "url-template";
import PassangerService from "@/services/PassangerService.ts";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import PassangerModel from "@/models/PassangerModel.ts";
import UserPublicModel from "@/models/UserPublicModel";

const useReviewsReportPassangers = (
  passanger?: PassangerModel,
  reporting?: boolean
) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const onQueryError = useQueryError();
  const { data: currentUser } = useCurrentUser();

  const query = useQuery({
    queryKey: ["passangersRR", passanger?.userId, reporting],
    queryFn: async () => {
      if (!passanger || !currentUser) {
        return undefined;
      }
      if (reporting) {
        const reportUri = parseTemplate(
          passanger.passengerReportsForTripUriTemplate as string
        ).expand({
          userId: currentUser?.userId as number,
        });
        return await PassangerService.getReport(reportUri);
      } else {
        const reviewUri = parseTemplate(
          passanger.passengerReviewsForTripUriTemplate as string
        ).expand({
          userId: currentUser?.userId as number,
        });
        return await PassangerService.getReview(reviewUri);
      }
    },
    retry: true,
    enabled: !!passanger && !!currentUser,
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

  const invalidatePassengerReviewQuery = (user: UserPublicModel) => {
    queryClient.invalidateQueries({
      queryKey: ["passangersRR", user.userId, false],
    });
  };

  const invalidatePassengerReportQuery = (user: UserPublicModel) => {
    queryClient.invalidateQueries({
      queryKey: ["passangersRR", user.userId, true],
    });
  };

  return {
    ...query,
    isLoading: isLoading || isPending,
    data: data,
    invalidatePassengerReviewQuery,
    invalidatePassengerReportQuery,
  };
};

export default useReviewsReportPassangers;
