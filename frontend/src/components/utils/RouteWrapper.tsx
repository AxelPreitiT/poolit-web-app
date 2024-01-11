import useAuthentication from "@/hooks/api/useAuthentication";
import { Helmet } from "react-helmet-async";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { homePath, loginPath } from "@/AppRouter";
import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import ToastType from "@/enums/ToastType";
import { useEffect } from "react";

interface RouterComponentProps {
  children: React.ReactNode;
  title?: string;
  showWhenUserIsAuthenticated?: boolean;
  showWhenUserIsNotAuthenticated?: boolean;
}

// This component is a wrapper of route pages
// Should be used to:
//  - set the title of the page
const RouteWrapper = ({
  children,
  title,
  showWhenUserIsAuthenticated = true,
  showWhenUserIsNotAuthenticated = true,
}: RouterComponentProps) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { isAuthenticated, loading } = useAuthentication();
  const addToast = useToastStackStore((state) => state.addToast);

  useEffect(() => {
    if (isAuthenticated && !showWhenUserIsAuthenticated) {
      addToast({
        type: ToastType.ERROR,
        message: t("route.error.already_authenticated"),
        timeout: defaultToastTimeout,
      });
      navigate(homePath, { replace: true });
    } else if (!isAuthenticated && !showWhenUserIsNotAuthenticated) {
      addToast({
        type: ToastType.ERROR,
        message: t("query.response.error.unauthorized"),
        timeout: defaultToastTimeout,
      });
      navigate(loginPath, { replace: true });
    }
  }, [
    isAuthenticated,
    loading,
    showWhenUserIsAuthenticated,
    showWhenUserIsNotAuthenticated,
    addToast,
    navigate,
    t,
  ]);

  // Todo: Create a loading screen
  if (loading) {
    return null;
  }

  return (
    <>
      <Helmet>
        <title>{(title ? `${t(title)} | ` : "") + `${t("poolit.name")}`}</title>
      </Helmet>
      {children}
    </>
  );
};

export default RouteWrapper;
