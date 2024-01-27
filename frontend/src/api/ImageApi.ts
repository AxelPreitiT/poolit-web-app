import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise } from "axios";

class ImageApi extends AxiosApi {

    public static getImage:(
        uri: string
    ) => AxiosPromise<any> = (uri : string) =>{
        return this.get<any>(uri,{
            headers:{
                "Accept":"image/webp"
            },
            timeout:5000,
            responseType: 'arraybuffer'
        })
    };
}

export default ImageApi;