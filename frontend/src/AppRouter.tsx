import { createBrowserRouter } from "react-router-dom";
import RouteWrapper from "./components/utils/RouteWrapper";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";
import RegisterPage from "./pages/register/RegisterPage";
import ProfilePage from "./pages/profile/ProfilePage";
import ReviewPageDriver from "./pages/publicReviews/ReviewPageDriver.tsx";
import PublicProfilePage from "@/pages/publicProfile/PublicProfilePage.tsx";
import Navbar from "./components/utils/Navbar";
import ReservedPage from "./pages/reserved/ReservedPage";
import CreatedPage from "./pages/created/CreatedPage";
import CreateTripPage from "./pages/createTrip/CreateTripPage";
import AdminPage from "./pages/admin/AdminPage";
import VerifyAccountPage from "./pages/verifyAccount/VerifyAccountPage";
import SearchPage from "./pages/search/SearchPage";
import ReviewPagePassanger from "@/pages/publicReviews/ReviewPagePassanger.tsx";
import ReportPage from "@/pages/admin/ReportPage.tsx";
import CreateCarPage from "./pages/createCar/CreateCarPage.tsx";
import CarPage from "./pages/car/CarPage.tsx";
import NotFoundPage from "./pages/notFound/notFound.tsx";

export const homePath = "/";
export const loginPath = "/login";
export const registerPath = "/register";
export const verifyAccountPath = "/verify";
export const tripDetailsPath = "/trips/:tripId";
export const profilePath = "/profile";
export const publicsDriverReviewsPath = "/reviews/driver/:id";
export const publicsPassangerReviewsPath = "/reviews/passanger/:id";
export const publicProfilePath = "/profile/:id";
export const reservedTripsPath = "/trips/reserved";
export const createdTripsPath = "/trips/created";
export const createTripsPath = "/trips/create";
export const adminPath = "/admin";
export const reportPath = "/reports/:id";
export const searchPath = "/search";
export const createCarsPath = "/cars/create";
export const carPath = "/cars/:carId";
export const routerBasename = import.meta.env.BASE_URL;

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
        <RouteWrapper title="trip_detail.title">
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
      path: publicsDriverReviewsPath,
      element: (
        <RouteWrapper
          title="reviews.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <ReviewPageDriver />
        </RouteWrapper>
      ),
    },
    {
      path: publicsPassangerReviewsPath,
      element: (
        <RouteWrapper
          title="reviews.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <ReviewPagePassanger />
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
    {
      path: reportPath,
      element: (
        <RouteWrapper
          title="admin.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <ReportPage />,
        </RouteWrapper>
      ),
    },
    {
      path: searchPath,
      element: (
        <RouteWrapper title="search.title">
          <Navbar />
          <SearchPage />,
        </RouteWrapper>
      ),
    },
    {
      path: createCarsPath,
      element: (
        <RouteWrapper
          title="create_car.title"
          showWhenUserIsNotAuthenticated={false}
        >
          <Navbar />
          <CreateCarPage />
        </RouteWrapper>
      ),
    },
    {
      path: carPath,
      element: (
        <RouteWrapper title="car.title" showWhenUserIsNotAuthenticated={false}>
          <Navbar />
          <CarPage />,
        </RouteWrapper>
      ),
    },
    {
      path: "*",
      element: (
          <RouteWrapper title="notFound.title" showWhenUserIsNotAuthenticated={false}>
              <Navbar />
              <NotFoundPage />,
          </RouteWrapper>
      ),
    },
  ],
  {
    basename: routerBasename,
  }
);

export default router;
