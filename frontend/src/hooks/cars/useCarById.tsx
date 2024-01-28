import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import useDiscovery from "../discovery/useDiscovery";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";
import CarService from "@/services/CarService";
import { useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";

const useCarById = (carId?: string) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const onQueryError = useQueryError();
  const {
    discovery,
    isLoading: isDiscoveryLoading,
    isError: isDiscoveryError,
  } = useDiscovery();

  const query = useQuery({
    queryKey: ["carId", carId],
    queryFn: async () => {
      if (!discovery || isDiscoveryError) {
        throw new DiscoveryMissingError();
      }
      return await CarService.getCarById(
        discovery.carsUriTemplate,
        carId as string
      );
    },
    retry: false,
    enabled: !!carId && !isDiscoveryLoading,
  });

  const { isLoading, isPending, isError, error, data: car } = query;

  useEffect(() => {
    if (isError) {
      const title = t("car.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "car.error.your",
      };
      onQueryError({
        error: error,
        timeout: defaultToastTimeout,
        title,
        customMessages,
      });
    }
  }, [isError, error, onQueryError, t]);

  const invalidate = () => {
    if (carId) {
      queryClient.invalidateQueries({
        queryKey: ["carId", carId],
      });
    }
  };

  return {
    ...query,
    isLoading: isLoading || isPending,
    car,
    invalidate,
  };
};

export default useCarById;
