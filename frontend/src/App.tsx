import { RouterProvider } from "react-router-dom";
import { Helmet, HelmetProvider } from "react-helmet-async";
import { useTranslation } from "react-i18next";
import PoolitFavicon from "@/images/favicon.svg";
import router from "./AppRouter";
import GlobalToastStack from "./components/toasts/GlobalToastStack";
import useApiLocale from "@/hooks/auth/useApiLocale";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { QueryClientProvider } from "@tanstack/react-query";
import { getQueryClient } from "./utils/query/queryClient";

const queryClient = getQueryClient();

const App = () => {
  const helmetContext = {};
  const { t } = useTranslation();
  useApiLocale();

  return (
    <HelmetProvider context={helmetContext}>
      {/* Default title in case no title is defined in a page */}
      <Helmet>
        <title>{t("poolit.name")}</title>
        <link rel="icon" type="image/svg+xml" href={PoolitFavicon} />
      </Helmet>
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
        <ReactQueryDevtools
          initialIsOpen={false}
          buttonPosition="bottom-left"
        />
      </QueryClientProvider>
      <GlobalToastStack />
    </HelmetProvider>
  );
};

export default App;
