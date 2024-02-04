import Service from "@/services/Service.ts";

enum ImageSize {
  Small = "SMALL",
  Medium = "MEDIUM",
  Large = "LARGE",
  Full = "FULL",
}

class ImageService extends Service {
  public static getSmallImageUrl = (uri: string): string => {
    const aux = new URL(uri);
    aux.searchParams.set("imageSize", ImageSize.Small.toString());
    return aux.toString();
  };
  public static getMediumImageUrl = (uri: string): string => {
    const aux = new URL(uri);
    aux.searchParams.set("imageSize", ImageSize.Medium.toString());
    return aux.toString();
  };
  public static getLargeImageUrl = (uri: string): string => {
    const aux = new URL(uri);
    aux.searchParams.set("imageSize", ImageSize.Large.toString());
    return aux.toString();
  };
}

export default ImageService;
