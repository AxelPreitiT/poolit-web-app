import UtilsService from "@/services/UtilsService";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";

const queryKey = "authentication";

const useAuthentication = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | undefined>(
    undefined
  );
  const queryClient = useQueryClient();

  const { isLoading, isError, isSuccess } = useQuery({
    queryKey: [queryKey],
    queryFn: UtilsService.tryAuthentication,
    retry: false,
  });

  useEffect(() => {
    if (isError) {
      setIsAuthenticated(false);
    }
    if (isSuccess) {
      setIsAuthenticated(true);
    }
  }, [isSuccess, isError]);

  const retryAuthentication = () =>
    queryClient.invalidateQueries({
      queryKey: [queryKey],
    });

  return {
    isAuthenticated,
    isLoading: isLoading || isAuthenticated === undefined,
    retryAuthentication,
  };
};

export default useAuthentication;
