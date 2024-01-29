import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import userService from "@/services/UserService.ts";
import { useQuery } from "@tanstack/react-query";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";
import { useEffect } from "react";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import { parseTemplate } from "url-template";

const usePublicUserById = (id?: string) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();

  const query = useQuery({
    queryKey: ["publicUser", id],
    queryFn: async () => {
      if (!id || !discovery?.usersUriTemplate) {
        return undefined;
      }
      const uri = parseTemplate(discovery.usersUriTemplate).expand({
        userId: id,
      });
      return await userService.getUserById(uri);
    },
    retry: false,
    enabled: !isLoadingDiscovery && !!discovery?.usersUriTemplate && !!id,
  });

  const { isError, error, data, isLoading, isPending } = query;

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("profile.error.title"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  return {
    ...query,
    isLoading: isLoading || isPending,
    user: data,
  };
};

export default usePublicUserById;
