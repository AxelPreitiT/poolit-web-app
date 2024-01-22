import { createBrowserRouter } from "react-router-dom";
import RouteWrapper from "./components/utils/RouteWrapper";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";
import RegisterPage from "./pages/register/RegisterPage";
import ProfilePage from "./pages/profile/ProfilePage";
import ReviewPage from "./pages/public/reviews/ReviewPage";
import PublicProfilePage from "./pages/profile/PublicProfilePage";
import Navbar from "./components/utils/Navbar";
import ReservedPage from "./pages/reserved/ReservedPage";
import CreatedPage from "./pages/created/CreatedPage";
import CreateTripPage from "./pages/createTrip/CreateTripPage";
import AdminPage from "./pages/admin/AdminPage";
import VerifyAccountPage from "./pages/verifyAccount/VerifyAccountPage";

export const homePath = "/";
export const loginPath = "/login";
export const registerPath = "/register";
export const verifyAccountPath = "/verify";
export const tripsPath = "/trips";
export const tripDetailsPath = "/trips/:tripId";
export const profilePath = "/profile";
export const publicsReviewsPath = "/reviews/drivers/:id";
export const publicProfilePath = "/profile/:id";
export const reservedTripsPath = "/trips/reserved";
export const createdTripsPath = "/trips/created";
export const createTripsPath = "/trips/create";
export const adminPath = "/admin";
export const searchPath = "/search";

const router = createBrowserRouter(
  [
    {
      path: homePath,
      element: (
        <RouteWrapper>
          <Navbar />
          <HomePage />
        </RouteWrapper>
      ),
    },
    {
      path: loginPath,
      element: (
        <RouteWrapper title="login.title" showWhenUserIsAuthenticated={false}>
          <LoginPage />
        </RouteWrapper>
      ),
    },
    {
      path: registerPath,
      element: (
        <RouteWrapper
          title="register.title"
          showWhenUserIsAuthenticated={false}
        >
          <RegisterPage />
        </RouteWrapper>
      ),
    },
    {
      path: verifyAccountPath,
      element: (
        <RouteWrapper
          title="verify_account.title"
          showWhenUserIsAuthenticated={false}
        >
          <VerifyAccountPage />
        </RouteWrapper>
      ),
    },
    {
      path: tripDetailsPath,
      element: (
        <RouteWrapper
          title="trip_detail.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <TripDetailsPage />,
        </RouteWrapper>
      ),
    },
    {
      path: profilePath,
      element: (
        <RouteWrapper
          title="profile.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <ProfilePage />
        </RouteWrapper>
      ),
    },
    {
      path: publicsReviewsPath,
      element: (
        <RouteWrapper
          title="reviews.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <ReviewPage />
        </RouteWrapper>
      ),
    },
    {
      path: publicProfilePath,
      element: (
        <RouteWrapper
          title="profile.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <PublicProfilePage />
        </RouteWrapper>
      ),
    },
    {
      path: reservedTripsPath,
      element: (
        <RouteWrapper
          title="reserved_trips.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <ReservedPage />,
        </RouteWrapper>
      ),
    },
    {
      path: createdTripsPath,
      element: (
        <RouteWrapper
          title="created_trips.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <CreatedPage />,
        </RouteWrapper>
      ),
    },
    {
      path: createTripsPath,
      element: (
        <RouteWrapper
          title="create_trip.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <CreateTripPage />,
        </RouteWrapper>
      ),
    },
    {
      path: adminPath,
      element: (
        <RouteWrapper
          title="admin.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <AdminPage />,
        </RouteWrapper>
      ),
    },
  ],
  {
    basename: import.meta.env.BASE_URL,
  }
);

export default router;
