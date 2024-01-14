import UtilsService from "@/services/UtilsService";
import { useQuery, useQueryClient } from "@tanstack/react-query";

const queryKey = "authentication";

const useAuthentication = () => {
  const queryClient = useQueryClient();

  const { isLoading, isSuccess, isError } = useQuery({
    queryKey: [queryKey],
    queryFn: UtilsService.tryAuthentication,
    retry: false,
  });

  const invalidateAuthentication = () =>
    queryClient.invalidateQueries({
      queryKey: [queryKey],
    });

  return {
    isAuthenticated: isSuccess && !isError,
    isLoading,
    invalidateAuthentication,
  };
};

export default useAuthentication;
