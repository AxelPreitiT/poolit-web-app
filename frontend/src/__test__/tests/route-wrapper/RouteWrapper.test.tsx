import { homePath, loginPath } from "@/AppRouter";
import CarFeatureMock from "@/__test__/mocks/CarFeatureMock";
import CityMock from "@/__test__/mocks/CityMock";
import { server } from "@/__test__/setup";
import { customRender, screen } from "@/__test__/utils";
import RouteWrapper from "@/components/utils/RouteWrapper";
import HomePage from "@/pages/home/HomePage";
import LoginPage from "@/pages/login/LoginPage";
import { setAuthToken } from "../utils";
import UserMock from "@/__test__/mocks/UserMock";
import TripMock from "@/__test__/mocks/TripMock";
import CarMock from "@/__test__/mocks/CarMock";

describe("RouteWrapper", () => {
  beforeEach(() => {
    server.use(
      CityMock.getAllCities(),
      CarFeatureMock.getAllFeatures(),
      TripMock.mockTripList(),
      CityMock.getCity(),
      CarMock.getCar()
    );
  });

  const expectToRenderPage = (path: string) => {
    expect(screen.queryAllByText(/must be logged in/i).length).toBe(0);
    expect(screen.queryAllByText(/already logged in/i).length).toBe(0);
    expect(window.location.pathname).toBe(path);
  };

  const expectToBeRedirectedToLogin = () => {
    expect(screen.queryAllByText(/must be logged in/i).length).toBeGreaterThan(
      0
    );
    expect(screen.queryAllByText(/already logged in/i).length).toBe(0);
    expect(window.location.pathname).toBe(loginPath);
  };

  const expectToBeRedirectedToHome = () => {
    expect(screen.queryAllByText(/must be logged in/i).length).toBe(0);
    expect(screen.queryAllByText(/already logged in/i).length).toBeGreaterThan(
      0
    );
    expect(window.location.pathname).toBe(homePath);
  };

  it("When user is not authenticated, and is allowed to see the page, should render the page", async () => {
    customRender(
      <RouteWrapper>
        <HomePage />
      </RouteWrapper>,
      { route: homePath }
    );

    expect(await screen.findByText(/travel with/i)).toBeVisible();
    expectToRenderPage(homePath);
  });

  it("When user is not authenticated, and is not allowed to see the page, should redirect to login", async () => {
    customRender(
      <RouteWrapper showWhenUserIsNotAuthenticated={false}>
        <HomePage />
      </RouteWrapper>,
      { route: homePath }
    );

    expectToBeRedirectedToLogin();
  });

  it("When user is authenticated, and is allowed to see the page, should render the page", async () => {
    setAuthToken();
    server.use(UserMock.getByIdPrivateRoleUser());
    customRender(
      <RouteWrapper showWhenUserIsNotAuthenticated={false}>
        <HomePage />
      </RouteWrapper>,
      { route: homePath }
    );

    expect(await screen.findByText(/travel with/i)).toBeVisible();
    expectToRenderPage(homePath);
  });

  it("When user is authenticated, and is not allowed to see the page, should redirect to home", async () => {
    setAuthToken();
    server.use(UserMock.getByIdPrivateRoleUser());
    customRender(
      <RouteWrapper showWhenUserIsAuthenticated={false}>
        <LoginPage />
      </RouteWrapper>,
      { route: loginPath }
    );

    expectToBeRedirectedToHome();
  });
});
