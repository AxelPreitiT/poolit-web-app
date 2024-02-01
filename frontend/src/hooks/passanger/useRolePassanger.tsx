import { useQuery, useQueryClient } from "@tanstack/react-query";
import PassangerService from "@/services/PassangerService.ts";
import { parseTemplate } from "url-template";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";

const useRolePassanger = (isDriver?: boolean, uri?: string) => {
  const { data: user } = useCurrentUser();
  const queryClient = useQueryClient();

  const query = useQuery({
    queryKey: ["rolePassanger", uri],
    queryFn: async () => {
      const id = String(user?.userId as number);
      const uriPassangers = parseTemplate(uri as string).expand({
        userId: id,
      });
      return await PassangerService.getPassangerRole(uriPassangers);
    },
    retry: false,
    enabled: !!uri && !isDriver,
  });

  const { isError, data, isLoading, isPending } = query;

  const invalidatePassangerRole = () => {
    queryClient.invalidateQueries({
      queryKey: ["rolePassanger"],
    });
  };

  return {
    ...query,
    isError,
    isLoading: isLoading || isPending,
    currentPassanger: data,
    isPending: isPending,
    invalidatePassangerRole,
  };
};

export default useRolePassanger;
