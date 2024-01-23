import UserService from "@/services/UserService";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useAuthentication from "../auth/useAuthentication";

export const useCurrentUser = () => {
  const { t } = useTranslation();
  const isAuthenticated = useAuthentication();
  const query = useQuery({
    queryKey: ["currentUser"],
    queryFn: UserService.getCurrentUser,
    retry: false,
    enabled: isAuthenticated,
  });

  const { isLoading, isError, error, data, isPending } = query;

  const onQueryError = useQueryError();

  useEffect(() => {
    if (isError) {
      const title = t("profile.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "profile.error.default",
      };
      onQueryError({
        error: error,
        timeout: defaultToastTimeout,
        title,
        customMessages,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    currentUser: data,
    isLoading: isLoading || isPending,
  };
};
