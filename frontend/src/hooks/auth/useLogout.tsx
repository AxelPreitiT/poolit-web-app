import { useNavigate } from "react-router";
import { loginPath } from "@/AppRouter";
import useSuccessToast from "../toasts/useSuccessToast";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";
import UserService from "@/services/UserService";
import { useQueryClient } from "@tanstack/react-query";

const useLogout = () => {
  const queryClient = useQueryClient();
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
    navigate(loginPath, { replace: true });
    await queryClient.removeQueries();
  };

  return logout;
};

export default useLogout;
