import { createCarsPath } from "@/AppRouter";
import { useLocation, useNavigate } from "react-router-dom";

const searchParamKey = "returnTo";

export const useReturnOnCarCreation = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const path = location.pathname;

  const navigateToCarCreationPage = () => {
    const searchParams = new URLSearchParams({ [searchParamKey]: path });
    navigate(`${createCarsPath}?${searchParams.toString()}`);
  };

  return navigateToCarCreationPage;
};

export const useNavigateOnCarCreation = () => {
  const { search } = useLocation();
  const navigate = useNavigate();

  const navigateOnCarCreation = (fallbackPath: string) => {
    const searchParams = new URLSearchParams(search);
    const returnTo = searchParams.get(searchParamKey);
    navigate(returnTo || fallbackPath, { replace: true });
  };

  return navigateOnCarCreation;
};
