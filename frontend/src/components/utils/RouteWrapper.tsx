import { Helmet } from "react-helmet-async";
import { useTranslation } from "react-i18next";
import useRouteAuthentication from "@/hooks/auth/useRouteAuthentication";

interface RouterComponentProps {
  children: React.ReactNode;
  title?: string;
  showWhenUserIsAuthenticated?: boolean;
  showWhenUserIsNotAuthenticated?: boolean;
}

// This component is a wrapper of route pages
// Should be used to:
//  - set the title of the page
//  - check if the user is authenticated
//  - show a loading screen
const RouteWrapper = ({
  children,
  title,
  showWhenUserIsAuthenticated = true,
  showWhenUserIsNotAuthenticated = true,
}: RouterComponentProps) => {
  const { t } = useTranslation();
  const { isLoadingAuth } = useRouteAuthentication({
    showWhenUserIsAuthenticated,
    showWhenUserIsNotAuthenticated,
  });

  // Todo: Create a loading screen
  if (isLoadingAuth) {
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
