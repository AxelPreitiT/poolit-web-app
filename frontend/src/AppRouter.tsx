import { createBrowserRouter } from "react-router-dom";
import RouteWithTitle from "./components/utils/RouteWithTitle";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripsPage from "./pages/trips/TripsPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";
import RegisterPage from "./pages/register/RegisterPage";

export const homePath = "/";
export const loginPath = "/login";
export const registerPath = "/register";
export const verifyEmailPath = "/verify-email";
export const tripsPath = "/trips";
export const tripDetailsPath = "/trips/:tripId";

const router = createBrowserRouter(
  [
    {
      path: homePath,
      element: <HomePage />,
    },
    {
      path: loginPath,
      element: (
        <RouteWithTitle title="login.title">
          <LoginPage />
        </RouteWithTitle>
      ),
    },
    {
      path: registerPath,
      element: (
        <RouteWithTitle title="register.title">
          <RegisterPage />
        </RouteWithTitle>
      ),
    },
    {
      path: tripsPath,
      element: <TripsPage />,
    },
    {
      path: tripDetailsPath,
      element: <TripDetailsPage />,
    },
  ],
  {
    basename: import.meta.env.BASE_URL,
  }
);

export default router;
