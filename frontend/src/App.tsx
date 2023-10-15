import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";
import TripsPage from "./pages/trips/TripsPage";
import TripDetailsPage from "./pages/trips/TripDetailsPage";

const router = createBrowserRouter(
  [
    { path: "/", Component: HomePage },
    { path: "/login", Component: LoginPage },
    { path: "/trips", Component: TripsPage },
    {
      path: "/trips/:tripId",
      Component: TripDetailsPage,
    },
  ],
  {
    basename: import.meta.env.BASE_URL,
  }
);

const App = () => {
  return <RouterProvider router={router} />;
};

export default App;
