import UtilsService from "@/services/UtilsService";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";

const useAuthentication = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(false);

  const { isLoading, isError, isSuccess } = useQuery({
    queryKey: ["authentication"],
    queryFn: UtilsService.tryAuthentication,
    retry: false,
  });

  useEffect(() => {
    setLoading(isLoading);
  }, [isLoading]);

  useEffect(() => {
    if (isError) {
      setIsAuthenticated(false);
    }
    if (isSuccess) {
      setIsAuthenticated(true);
    }
  }, [isSuccess, isError]);

  return { isAuthenticated, loading };
};

export default useAuthentication;
