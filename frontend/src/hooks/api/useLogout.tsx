import { useNavigate } from "react-router";
import useAuthentication from "./useAuthentication";
import { loginPath } from "@/AppRouter";
import useSuccessToast from "../toasts/useSuccessToast";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";
import UserService from "@/services/UserService";

const useLogout = () => {
  const { invalidateAuthentication } = useAuthentication();
  const showSuccessToast = useSuccessToast();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const logout = async () => {
    UserService.logout();
    showSuccessToast({
      title: t("logout.title"),
      timeout: defaultToastTimeout,
      message: t("logout.message"),
    });
    await invalidateAuthentication();
    navigate(loginPath, { replace: true });
  };

  return logout;
};

export default useLogout;
