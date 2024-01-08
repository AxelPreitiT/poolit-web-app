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
import Navbar from "./components/utils/Navbar";
import ReservedPage from "./pages/reserved/ReservedPage";
import CreatedPage from "./pages/created/CreatedPage";
import CreateTripPage from "./pages/createTrip/CreatedPage";
import AdminPage from "./pages/admin/AdminPage";

export const homePath = "/";
export const loginPath = "/login";
export const registerPath = "/register";
export const verifyEmailPath = "/verify-email";
export const tripsPath = "/trips";
export const tripDetailsPath = "/trips/:tripId";
export const profilePath = "/profile";
export const publicsReviewsPath = "/reviews/drivers/:id";
export const publicProfilePath = "/profile/:id";
export const reservedTripsPath = "/trips/reserved";
export const createdTripsPath = "/trips/created";
export const createTripsPath = "/trips/create";
export const adminPath = "/admin";

const router = createBrowserRouter(
  [
    {
      path: homePath,
      element: (
        <RouteWrapper title="poolit.name">
          <Navbar />
          <HomePage />
        </RouteWrapper>
      ),
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
      element: (
        <RouteWrapper title="trip_detail.title">
          <Navbar />
          <TripDetailsPage />,
        </RouteWrapper>
      ),
    },
    {
      path: profilePath,
      element: (
        <RouteWrapper title="profile.title">
          <Navbar />
          <ProfilePage />
        </RouteWrapper>
      ),
    },
    {
      path: publicsReviewsPath,
      element: (
        <RouteWrapper title="reviews.title">
          <Navbar />
          <ReviewPage />
        </RouteWrapper>
      ),
    },
    {
      path: publicProfilePath,
      element: (
        <RouteWrapper title="profile.title">
          <Navbar />
          <PublicProfilePage />
        </RouteWrapper>
      ),
    },
    {
      path: reservedTripsPath,
      element: (
        <RouteWrapper title="reserved_trips.title">
          <Navbar />
          <ReservedPage />,
        </RouteWrapper>
      ),
    },
    {
      path: createdTripsPath,
      element: (
        <RouteWrapper title="created_trips.title">
          <Navbar />
          <CreatedPage />,
        </RouteWrapper>
      ),
    },
    {
      path: createTripsPath,
      element: (
        <RouteWrapper title="create_trip.title">
          <Navbar />
          <CreateTripPage />,
        </RouteWrapper>
      ),
    },
    {
      path: adminPath,
      element: (
        <RouteWrapper title="admin.title">
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
