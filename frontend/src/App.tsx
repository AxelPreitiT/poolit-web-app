import { RouterProvider } from "react-router-dom";
import { Helmet, HelmetProvider } from "react-helmet-async";
import { useTranslation } from "react-i18next";
import PoolitFavicon from "@/images/favicon.svg";
import router from "./AppRouter";
import GlobalToastStack from "./components/toasts/GlobalToastStack";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

const App = () => {
  const helmetContext = {};
  const { t } = useTranslation();

  return (
    <HelmetProvider context={helmetContext}>
      {/* Default title in case no title is defined in a page */}
      <Helmet>
        <title>{t("poolit.name")}</title>
        <link rel="icon" type="image/svg+xml" href={PoolitFavicon} />
      </Helmet>
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
      <GlobalToastStack />
    </HelmetProvider>
  );
};

export default App;
