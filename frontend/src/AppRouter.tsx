import { createBrowserRouter } from "react-router-dom";
import RouteWithTitle from "./components/utils/RouteWithTitle";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripsPage from "./pages/trips/TripsPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";
import RegisterPage from "./pages/register/RegisterPage";
import ProfilePage from "./pages/profile/ProfilePage";
import ReviewPage from "./pages/public/reviews/ReviewPage";

export const homePath = "/";
export const loginPath = "/login";
export const registerPath = "/register";
export const verifyEmailPath = "/verify-email";
export const tripsPath = "/trips";
export const tripDetailsPath = "/trips/:tripId";
export const profilePath = "/profile";
export const PublicsReviewsPath = "/reviews/drivers/:id";

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
    {
      path: profilePath,
      element: <ProfilePage />,
    },
    {
      path: PublicsReviewsPath,
      element: <ReviewPage />,
    },
  ],
  {
    basename: import.meta.env.BASE_URL,
  }
);

export default router;
