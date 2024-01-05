import { createBrowserRouter } from "react-router-dom";
import RouteWrapper from "./components/utils/RouteWrapper";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripsPage from "./pages/trips/TripsPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";
import RegisterPage from "./pages/register/RegisterPage";
import ProfilePage from "./pages/profile/ProfilePage";
import ReviewPage from "./pages/public/reviews/ReviewPage";
import PublicProfilePage from "./pages/profile/PublicProfilePage";
import NavbarWrapper from "./components/utils/Navbar";

export const homePath = "/";
export const loginPath = "/login";
export const registerPath = "/register";
export const verifyEmailPath = "/verify-email";
export const tripsPath = "/trips";
export const tripDetailsPath = "/trips/:tripId";
export const profilePath = "/profile";
export const PublicsReviewsPath = "/reviews/drivers/:id";
export const PublicProfilePath = "/profile/:id";

const router = createBrowserRouter(
  [
    {
      path: homePath,
      element: <HomePage />,
    },
    {
      path: loginPath,
      element: (
        <RouteWrapper title="login.title">
          <LoginPage />
        </RouteWrapper>
      ),
    },
    {
      path: registerPath,
      element: (
        <RouteWrapper title="register.title">
          <RegisterPage />
        </RouteWrapper>
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
      element: (
        <RouteWrapper title="register.title">
          <NavbarWrapper />
          <ProfilePage />,
        </RouteWrapper>
      ),
    },
    {
      path: PublicsReviewsPath,
      element: <ReviewPage />,
    },
    {
      path: PublicProfilePath,
      element: <PublicProfilePage />,
    },
  ],
  {
    basename: import.meta.env.BASE_URL,
  }
);

export default router;
