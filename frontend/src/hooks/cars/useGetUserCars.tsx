import { useTranslation } from "react-i18next";
import { useCurrentUser } from "../users/useCurrentUser";
import CarService from "@/services/CarService";
import { useQuery } from "@tanstack/react-query";
import useQueryError from "../errors/useQueryError";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";

const useGetUserCars = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const {
    isLoading: isCurrentUserLoading,
    currentUser,
    isError: isCurrentUserError,
  } = useCurrentUser();

  const query = useQuery({
    queryKey: ["userCars"],
    queryFn: async () => {
      if (currentUser === undefined) {
        return;
      }
      return await CarService.getCarsByUser(currentUser);
    },
    enabled: !isCurrentUserLoading && !isCurrentUserError,
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

export default useGetUserCars;
