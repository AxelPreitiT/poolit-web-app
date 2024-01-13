import { useTranslation } from "react-i18next";
import useAuthentication from "./useAuthentication";
import { useCallback, useEffect, useState } from "react";
import { homePath, loginPath } from "@/AppRouter";
import ToastType from "@/enums/ToastType";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { NavigateOptions, useLocation, useNavigate } from "react-router-dom";

const useRouteAuthentication = ({
  showWhenUserIsAuthenticated = true,
  showWhenUserIsNotAuthenticated = true,
}: {
  showWhenUserIsAuthenticated?: boolean;
  showWhenUserIsNotAuthenticated?: boolean;
}) => {
  const { t } = useTranslation();
  const { isAuthenticated, isLoading } = useAuthentication();
  const [allowRender, setAllowRender] = useState(false);
  const addToast = useToastStackStore((state) => state.addToast);
  const location = useLocation();
  const navigate = useNavigate();

  const onInvalid = useCallback(
    (
      toastType: ToastType,
      toastKey: string,
      path: string,
      options: NavigateOptions = {}
    ) => {
      addToast({
        type: toastType,
        message: t(toastKey),
        timeout: defaultToastTimeout,
      });
      navigate(path, {
        ...options,
        replace: true,
      });
    },
    [addToast, navigate, t]
  );

  useEffect(() => {
    if (isLoading) {
      return;
    }
    if (isAuthenticated && !showWhenUserIsAuthenticated) {
      onInvalid(
        ToastType.WARNING,
        "route.error.already_authenticated",
        homePath
      );
    } else if (!isAuthenticated && !showWhenUserIsNotAuthenticated) {
      onInvalid(ToastType.ERROR, "route.error.not_authenticated", loginPath, {
        state: { from: location.pathname },
      });
    } else {
      setAllowRender(true);
    }
  }, [
    isLoading,
    isAuthenticated,
    onInvalid,
    showWhenUserIsAuthenticated,
    showWhenUserIsNotAuthenticated,
    location.pathname,
  ]);

  return { isLoadingAuth: !allowRender };
};

export default useRouteAuthentication;
