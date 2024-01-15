import UserService from "@/services/UserService";
import { useQuery } from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import useSuccessToast from "../toasts/useSuccessToast";
import { useCallback, useEffect } from "react";
import { useTranslation } from "react-i18next";
import UnauthorizedResponseError from "@/errors/UnauthorizedResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { useNavigate } from "react-router-dom";
import { homePath, loginPath } from "@/AppRouter";
import useAuthentication from "./useAuthentication";
import EmailMissingError from "@/errors/EmailMissingError";
import TokenMissingError from "@/errors/TokenMissingError";

const useVerifyAccount = (email: string | null, token: string | null) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const showSuccessToast = useSuccessToast();
  const navigate = useNavigate();
  const { invalidateAuthentication } = useAuthentication();

  const query = useQuery({
    queryKey: ["verifyAccount", email, token],
    queryFn: async ({ queryKey }) => {
      const [, email, token] = queryKey;
      if (!email) {
        throw new EmailMissingError();
      }
      if (!token) {
        throw new TokenMissingError();
      }
      await UserService.verifyAccount(email, token);
      return true;
    },
    retry: false,
  });

  const { isSuccess, isError, error } = query;

  const onSuccess = useCallback(() => {
    showSuccessToast({
      title: t("verify_account.success.title"),
      message: t("verify_account.success.message"),
      timeout: defaultToastTimeout,
    });
    navigate(homePath, { replace: true });
    invalidateAuthentication();
  }, [showSuccessToast, t, navigate, invalidateAuthentication]);

  const onError = useCallback(
    (error: Error) => {
      const title = t("verify_account.error.title");
      const timeout = defaultToastTimeout;
      const customMessages = {
        [UnauthorizedResponseError.ERROR_ID]:
          "verify_account.error.invalid_token",
        [UnknownResponseError.ERROR_ID]: "verify_account.error.default",
      };
      onQueryError({ error, title, timeout, customMessages });
      navigate(loginPath, { replace: true });
    },
    [onQueryError, t, navigate]
  );

  useEffect(() => {
    if (isError) {
      onError(error);
    }
    if (isSuccess) {
      onSuccess();
    }
  }, [isSuccess, isError, error, onSuccess, onError]);

  return query;
};

export default useVerifyAccount;
