import BaseMock from "@/__test__/mocks/BaseMock.ts";
import CarBrandModel from "@/models/CarBrandModel.ts";

class CarBrandMock extends BaseMock {
  public static readonly CAR_BRANDS_PATH = "/car-brands";

  public static readonly CAR_BRANDS: CarBrandModel[] = [
    {
      id: "VOLKSWAGEN",
      name: "Volkswagen",
      selfUri: this.getPath("/VOLKSWAGEN"),
    },
    {
      id: "FORD",
      name: "Ford",
      selfUri: this.getPath("/FORD"),
    },
    {
      id: "CHEVROLET",
      name: "Chevrolet",
      selfUri: this.getPath("/CHEVROLET"),
    },
    {
      id: "RENAULT",
      name: "Renault",
      selfUri: this.getPath("/RENAULT"),
    },
    {
      id: "FIAT",
      name: "Fiat",
      selfUri: this.getPath("/FIAT"),
    },
    {
      id: "PEUGEOT",
      name: "Peugeot",
      selfUri: this.getPath("/PEUGEOT"),
    },
    {
      id: "TOYOTA",
      name: "Toyota",
      selfUri: this.getPath("/TOYOTA"),
    },
    {
      id: "HONDA",
      name: "Honda",
      selfUri: this.getPath("/HONDA"),
    },
    {
      id: "CITROEN",
      name: "Citroen",
      selfUri: this.getPath("/CITROEN"),
    },
    {
      id: "HYUNDAI",
      name: "Hyundai",
      selfUri: this.getPath("/HYUNDAI"),
    },
    {
      id: "MERCEDES_BENZ",
      name: "Mercedes Benz",
      selfUri: this.getPath("/MERCEDES_BENZ"),
    },
    {
      id: "BMW",
      name: "BMW",
      selfUri: this.getPath("/BMW"),
    },
    {
      id: "AUDI",
      name: "Audi",
      selfUri: this.getPath("/AUDI"),
    },
    {
      id: "KIA",
      name: "Kia",
      selfUri: this.getPath("/KIA"),
    },
    {
      id: "MAZDA",
      name: "Mazda",
      selfUri: this.getPath("/MAZDA"),
    },
    {
      id: "NISSAN",
      name: "Nissan",
      selfUri: this.getPath("/NISSAN"),
    },
    {
      id: "SUZUKI",
      name: "Suzuki",
      selfUri: this.getPath("/SUZUKI"),
    },
    {
      id: "JEEP",
      name: "Jeep",
      selfUri: this.getPath("/JEEP"),
    },
    {
      id: "LAND_ROVER",
      name: "Land Rover",
      selfUri: this.getPath("/LAND_ROVER"),
    },
    {
      id: "VOLVO",
      name: "Volvo",
      selfUri: this.getPath("/VOLVO"),
    },
    {
      id: "UNKNOWN",
      name: "Unknown",
      selfUri: this.getPath("/UNKNOWN"),
    },
  ];

  public static getPath(path: string): string {
    return super.getPath(`${this.CAR_BRANDS_PATH}${path}`);
  }

  public static getCarBrandByIdProp(id: string) {
    return this.CAR_BRANDS.find((car) => car.id === id) || this.CAR_BRANDS[0];
  }

  public static getCarBrandByUriProp(uri: string) {
    return (
      this.CAR_BRANDS.find((car) => car.selfUri === uri) || this.CAR_BRANDS[0]
    );
  }

  public static getAllBrands() {
    return this.get("/car-brands", () =>
      this.jsonResponse(this.CAR_BRANDS, { status: this.OK_STATUS })
    );
  }

  public static getBrand() {
    return this.get("/car-brands/:brandId", () =>
      this.jsonResponse(this.getCarBrandByIdProp("VOLVO"), {
        status: this.OK_STATUS,
      })
    );
  }
}

export default CarBrandMock;
