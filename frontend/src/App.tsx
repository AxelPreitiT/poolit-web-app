import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomePage from "./pages/home/HomePage";
import LoginPage from "./pages/login/LoginPage";

const router = createBrowserRouter(
  [
    { path: "/", Component: HomePage },
    { path: "/login", Component: LoginPage },
  ],
  {
    basename: import.meta.env.PUBLIC_BASE_PATH,
  }
);

const App = () => {
  return <RouterProvider router={router} />;
};

export default App;
