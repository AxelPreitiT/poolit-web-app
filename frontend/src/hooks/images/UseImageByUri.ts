// import { useQuery } from "@tanstack/react-query";
// import ImageService from "@/services/ImageService.ts";
// import {useEffect} from "react";
//
//
// const useImageByUri = (
//     imageUri:string,
//     {
//         enabled = true,
//     }:{
//         enabled?: boolean;
//     }={}) => {
//     const {
//         isLoading,
//         isError,
//         data: image,
//         error,
//         isPending,
//     } = useQuery({
//         queryKey: ["image", imageUri],
//         queryFn: async ({ queryKey }): Promise<any> => {
//             const [, imageUri] = queryKey;
//             return await ImageService.getImage(imageUri);
//         },
//         retry: false,
//         enabled,
//     });
//
//     useEffect(() => {
//         if (isError) {
//            console.log("ERROR!!!!")
//         }
//     }, [isError, error]);
//     return {
//         isLoading: isLoading || isPending,
//         image,
//         isError,
//         error,
//     };
// };

// export default useImageByUri;