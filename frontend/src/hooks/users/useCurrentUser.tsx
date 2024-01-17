import UserService from "@/services/UserService";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import QueryError from "@/errors/QueryError";

export const useCurrentUser = ({
    enabled = true
}: {enabled?:boolean}={}) => {
  const { isLoading, isError, data, error, isPending } = useQuery({
    queryKey: ["currentUser"],
    queryFn: UserService.getCurrentUser,
    retry: false,
    enabled
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

  return {isLoading: isLoading || isPending, currentUser:data };
};
