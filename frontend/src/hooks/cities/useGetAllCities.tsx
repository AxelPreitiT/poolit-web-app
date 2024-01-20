import CityService from "@/services/CityService";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";

const useGetAllCities = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["cities"],
    queryFn: CityService.getAllCities,
  });

  const { isError, error, data } = query;

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("city.error.title"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    cities: data,
  };
};

export default useGetAllCities;
