import { useTranslation } from "react-i18next";
import useQueryError from "@/hooks/errors/useQueryError.tsx";
import userService from "@/services/UserService.ts";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { defaultToastTimeout } from "@/components/toasts/ToastProps.ts";

const usePublicUserByUri = (uri?: string) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["publicUserUri", uri],
    queryFn: async () => {
      if (!uri) {
        return undefined;
      }
      return await userService.getUserByUri(uri);
    },
    retry: false,
    enabled: !!uri,
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

  const invalidateUserState = () => {
    queryClient.invalidateQueries({
      queryKey: ["publicUserUri", uri],
    });
  };

  return {
    ...query,
    isLoading: isLoading || isPending,
    user: data,
    invalidateUserState,
  };
};

export default usePublicUserByUri;
