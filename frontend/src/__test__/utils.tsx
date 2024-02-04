import GlobalToastStack from "@/components/toasts/GlobalToastStack";
import { getQueryClient } from "@/utils/query/queryClient";
import { QueryClientProvider } from "@tanstack/react-query";
import { I18nextProvider } from "react-i18next";
import i18nTest from "./i18n";
import { ReactElement } from "react";
import { RenderOptions, render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import userEvent from "@testing-library/user-event";
import { HelmetProvider } from "react-helmet-async";

export const queryClient = getQueryClient();

// eslint-disable-next-line react-refresh/only-export-components
const AllProviders = ({ children }: { children: React.ReactNode }) => {
  return (
    <HelmetProvider context={{}}>
      <QueryClientProvider client={queryClient}>
        <I18nextProvider i18n={i18nTest}>
          <BrowserRouter>
            {children}
            <GlobalToastStack />
          </BrowserRouter>
        </I18nextProvider>
      </QueryClientProvider>
    </HelmetProvider>
  );
};

export const customRender = (
  ui: ReactElement,
  { route = "/", ...options }: RenderOptions & { route?: string } = {}
) => {
  window.history.pushState({}, "Test page", route);
  return {
    user: userEvent.setup(),
    ...render(ui, { wrapper: AllProviders, ...options }),
  };
};

// eslint-disable-next-line react-refresh/only-export-components
export * from "@testing-library/react";
