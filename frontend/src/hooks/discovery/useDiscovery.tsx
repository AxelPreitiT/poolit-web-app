import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import DiscoveryService from "@/services/DiscoveryService";
import { useCallback, useEffect } from "react";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";

const useDiscovery = () => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["discovery"],
    queryFn: DiscoveryService.getDiscovery,
    retry: false,
  });

  const { isError, error, data: discovery, isLoading } = query;

  const getDiscoveryOnMount = useCallback(async () => {
    if (discovery) {
      return discovery;
    }
    try {
      if (isLoading) {
        return await DiscoveryService.getDiscovery();
      }
      throw new DiscoveryMissingError();
    } catch (error) {
      throw new DiscoveryMissingError();
    }
  }, [discovery, isLoading]);

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
    getDiscoveryOnMount,
  };
};

export default useDiscovery;
