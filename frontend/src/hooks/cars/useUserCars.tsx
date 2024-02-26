import { useTranslation } from "react-i18next";
import { useCurrentUser } from "../users/useCurrentUser";
import CarService from "@/services/CarService";
import { useQuery } from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import useAuthentication from "../auth/useAuthentication";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";

const useUserCars = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const isAuthenticated = useAuthentication();
  const {
    isLoading: isCurrentUserLoading,
    currentUser,
    isError: isCurrentUserError,
  } = useCurrentUser();
  const {
    isLoading: isDiscoveryLoading,
    discovery,
    isError: isDiscoveryError,
  } = useDiscovery();

  const query = useQuery({
    queryKey: ["userCars"],
    queryFn: async () => {
      if (currentUser === undefined) {
        return;
      }
      if (!discovery || isDiscoveryError) {
        throw new DiscoveryMissingError();
      }
      return await CarService.getCarsByUser(
        discovery.carsUriTemplate,
        currentUser
      );
    },
    enabled:
      isAuthenticated &&
      !isCurrentUserLoading &&
      !isCurrentUserError &&
      !isDiscoveryLoading,
    retry: false,
  });

  const { isLoading, isPending, isError, error, data } = query;

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("car.error.yours"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    isLoading: isLoading || isPending,
    cars: data,
  };
};

export default useUserCars;
