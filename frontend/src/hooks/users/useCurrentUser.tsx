import UserService from "@/services/UserService";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import QueryError from "@/errors/QueryError";

export const useCurrentUser = () => {
  const { isLoading, isError, data, error } = useQuery({
    queryKey: ["currentUser"],
    queryFn: UserService.getCurrentUser,
    retry: false,
  });

  const onQueryError = useQueryError();

  useEffect(() => {
    if (isError) {
      onQueryError({
        error: error as QueryError,
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError]);

  return { isLoading, currentUser: data };
};
