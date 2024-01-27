import passangerService from "@/services/PassangerService.ts";

const useAcceptPassangerByUri = (uri?: string) => {

    const onSubmit = async () => {
        if (uri == undefined) {
            return null;
        }
        return await passangerService.postAcceptPassangersByUri(uri as string);
    };

    return {
        onSubmit
    }

}

export default useAcceptPassangerByUri;