import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import DiscoveryService from "@/services/DiscoveryService";
import { useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";

const useDiscovery = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["discovery"],
    queryFn: DiscoveryService.getDiscovery,
    retry: false,
  });

  const { isError, error, data: discovery } = query;

  useEffect(() => {
    if (isError) {
      const title = t("discovery.error.title");
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: "discovery.error.default",
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
    discovery,
  };
};

export default useDiscovery;
