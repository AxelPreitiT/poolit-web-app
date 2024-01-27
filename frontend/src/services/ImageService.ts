import Service from "@/services/Service.ts";
// import ImageApi from "@/api/ImageApi.ts";

enum ImageSize{
    Small = "SMALL",
    Medium = "MEDIUM",
    Large = "LARGE",
    Full = "FULL"
}

class ImageService extends Service {

    public static getSmallImageUrl = (uri: string): string =>{
        const aux = new URL(uri);
        aux.searchParams.set("imageSize",ImageSize.Small.toString());
        return aux.toString()
    }
    public static getMediumImageUrl = (uri: string): string =>{
        const aux = new URL(uri);
        aux.searchParams.set("imageSize",ImageSize.Medium.toString());
        return aux.toString()
    }
    public static getLargeImageUrl = (uri: string): string =>{
        const aux = new URL(uri);
        aux.searchParams.set("imageSize",ImageSize.Large.toString());
        return aux.toString()
    }

    // public static getImage = async (
    //     uri: string,
    //     size: ImageSize = ImageSize.Small,
    //     // defaultImage: File
    // ):Promise<any> => {
    //     size.toString()
    //     const aux = new URL(uri);
    //     aux.searchParams.set("imageSize",size.toString())
    //     const aux_res = await ImageApi.getImage(aux.toString());
    //     const base64 = btoa(
    //         new Uint8Array(aux_res.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
    //     );
    //     return `data:image/jpeg;base64,${base64}`
    //     // const imageObjectURL = URL.createObjectURL(aux_res.data);
    //     // console.log(imageObjectURL)
    //     // console.log(base64);
    //     // const ans = await this.resolveQuery(ImageApi.getImage(aux.toString()))
    //     // console.log(aux_res)
    //     // return ans
    // }
}

export default ImageService;