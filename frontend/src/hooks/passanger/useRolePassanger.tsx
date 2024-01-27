import {useQuery} from "@tanstack/react-query";
import PassangerService from "@/services/PassangerService.ts";
import {parseTemplate} from "url-template";
import UserPrivateModel from "@/models/UserPrivateModel.ts";

const useRolePassanger = ( isDriver:boolean, currentUser?: UserPrivateModel, uri?: string) => {

    const query = useQuery({
        queryKey: ["rolePassanger"],
        queryFn: async () => {
            if (!uri || currentUser === undefined) {
                return undefined;
            }
            const params = currentUser.selfUri.split("/");
            const id = params[params.length - 1];
            const uriAllPassangers = parseTemplate(uri).expand({
                userId: id  ,
            });
            return await PassangerService.getPassangerRole(uriAllPassangers);
        },
        retry: false,
        enabled: !!uri && !isDriver,
    });

    const { isError, data, isLoading, isPending } = query;

    return {
        ...query,
        isError,
        isLoading: isLoading || isPending,
        passangersRole: data,
    };
};

export default useRolePassanger;