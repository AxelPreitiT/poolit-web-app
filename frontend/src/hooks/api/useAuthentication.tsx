import UtilsService from "@/services/UtilsService";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";

const useAuthentication = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const { isLoading, isError, isSuccess } = useQuery({
    queryKey: ["authentication"],
    queryFn: async () => {
      await UtilsService.tryAuthentication();
      return isAuthenticated;
    },
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

  return { isAuthenticated, isLoading };
};

export default useAuthentication;
