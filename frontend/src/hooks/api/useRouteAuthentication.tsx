import { useTranslation } from "react-i18next";
import useAuthentication from "./useAuthentication";
import { useCallback, useEffect } from "react";
import { homePath, loginPath } from "@/AppRouter";
import ToastType from "@/enums/ToastType";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { useNavigate } from "react-router-dom";

const useRouteAuthentication = ({
  showWhenUserIsAuthenticated = true,
  showWhenUserIsNotAuthenticated = true,
}: {
  showWhenUserIsAuthenticated?: boolean;
  showWhenUserIsNotAuthenticated?: boolean;
}) => {
  const { t } = useTranslation();
  const { isAuthenticated, isLoading } = useAuthentication();
  const addToast = useToastStackStore((state) => state.addToast);
  const navigate = useNavigate();

  const onError = useCallback(
    (toastKey: string, path: string) => {
      addToast({
        type: ToastType.ERROR,
        message: t(toastKey),
        timeout: defaultToastTimeout,
      });
      navigate(path, { replace: true });
    },
    [addToast, navigate, t]
  );

  useEffect(() => {
    if (isLoading) {
      return;
    }
    if (isAuthenticated && !showWhenUserIsAuthenticated) {
      onError("route.error.already_authenticated", homePath);
    } else if (!isAuthenticated && !showWhenUserIsNotAuthenticated) {
      onError("query.response.error.not_authenticated", loginPath);
    }
  }, [
    isLoading,
    isAuthenticated,
    onError,
    showWhenUserIsAuthenticated,
    showWhenUserIsNotAuthenticated,
  ]);

  return { isLoadingAuth: isLoading };
};

export default useRouteAuthentication;
