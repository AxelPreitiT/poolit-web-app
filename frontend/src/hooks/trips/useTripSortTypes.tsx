import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import useDiscovery from "../discovery/useDiscovery";
import { useQuery } from "@tanstack/react-query";
import TripSortTypesService from "@/services/TripSortTypesService";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";

const useTripSortTypes = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const { getDiscoveryOnMount } = useDiscovery();

  const query = useQuery({
    queryKey: ["tripSortTypes"],
    queryFn: async () => {
      const discovery = await getDiscoveryOnMount();
      return await TripSortTypesService.getTripSortTypes(
        discovery.tripSortTypesUriTemplate
      );
    },
    retry: false,
  });

  const { isError, error, data: tripSortTypes } = query;

  useEffect(() => {
    if (isError) {
      const title = t("trip_sort_types.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "trip_sort_types.error.default",
      };
      onQueryError({
        error: error,
        title,
        customMessages,
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    tripSortTypes,
  };
};

export default useTripSortTypes;
