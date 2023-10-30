import { createBrowserRouter } from "react-router-dom";
import RouteWithTitle from "./components/utils/RouteWithTitle";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripsPage from "./pages/trips/TripsPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";

const router = createBrowserRouter(
  [
    {
      path: "/",
      element: <HomePage />,
    },
    {
      path: "/login",
      element: (
        <RouteWithTitle title="login.title">
          <LoginPage />
        </RouteWithTitle>
      ),
    },
    {
      path: "/trips",
      element: <TripsPage />,
    },
    {
      path: "/trips/:tripId",
      element: <TripDetailsPage />,
    },
  ],
  {
    basename: import.meta.env.BASE_URL,
  }
);

export default router;
