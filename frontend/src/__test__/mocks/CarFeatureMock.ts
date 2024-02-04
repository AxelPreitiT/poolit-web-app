import BaseMock from "@/__test__/mocks/BaseMock.ts";
import CarFeatureModel from "@/models/CarFeatureModel";

class CarFeatureMock extends BaseMock {
  public static readonly CAR_FEATURES_PATH = "/car-features";

  public static readonly CAR_FEATURES: CarFeatureModel[] = [
    {
      id: "AIR",
      name: "Air conditioner",
      selfUri: this.getPath("/AIR"),
    },
    {
      id: "PET_FRIENDLY",
      name: "Pet friendly",
      selfUri: this.getPath("/PET_FRIENDLY"),
    },
    {
      id: "MUSIC",
      name: "Music",
      selfUri: this.getPath("/MUSIC"),
    },
    {
      id: "TRUNK_SPACE",
      name: "Trunk space",
      selfUri: this.getPath("/TRUNK_SPACE"),
    },
  ];

  protected static getPath(path: string): string {
    return super.getPath(`${this.CAR_FEATURES_PATH}${path}`);
  }

  public static getCarFeatureByIdProp(id: string) {
    return (
      this.CAR_FEATURES.find((carFeature) => carFeature.id === id) ||
      this.CAR_FEATURES[0]
    );
  }

  public static getCarFeatureByUriProp(uri: string) {
    return (
      this.CAR_FEATURES.find((carFeature) => carFeature.selfUri === uri) ||
      this.CAR_FEATURES[0]
    );
  }

  public static getAllFeatures() {
    return this.get("/car-features", () =>
      this.jsonResponse(this.CAR_FEATURES, { status: this.OK_STATUS })
    );
  }
  public static getFeature() {
    return this.get("/car-features/:featureId", () =>
      this.jsonResponse(this.getCarFeatureByIdProp("TRUNK_SPACE"), {
        status: this.OK_STATUS,
      })
    );
  }
}
export default CarFeatureMock;
