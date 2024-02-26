import BaseMock from "@/__test__/mocks/BaseMock.ts";
import CityModel from "@/models/CityModel.ts";

class CityMock extends BaseMock {
  public static readonly CITIES_PATH = "/cities";

  public static CITIES: CityModel[] = [
    {
      id: 1,
      name: "Agronomía",
      selfUri: this.getPath("/1"),
    },
    {
      id: 2,
      name: "Almagro",
      selfUri: this.getPath("/2"),
    },
    {
      id: 3,
      name: "Balvanera",
      selfUri: this.getPath("/3"),
    },
    {
      id: 4,
      name: "Barracas",
      selfUri: this.getPath("/4"),
    },
    {
      id: 5,
      name: "Belgrano",
      selfUri: this.getPath("/5"),
    },
    {
      id: 6,
      name: "Boedo",
      selfUri: this.getPath("/6"),
    },
    {
      id: 7,
      name: "Caballito",
      selfUri: this.getPath("/7"),
    },
    {
      id: 8,
      name: "Chacarita",
      selfUri: this.getPath("/8"),
    },
    {
      id: 9,
      name: "Coghlan",
      selfUri: this.getPath("/9"),
    },
    {
      id: 10,
      name: "Colegiales",
      selfUri: this.getPath("/10"),
    },
    {
      id: 11,
      name: "Constitución",
      selfUri: this.getPath("/11"),
    },
    {
      id: 12,
      name: "Flores",
      selfUri: this.getPath("/12"),
    },
    {
      id: 13,
      name: "Floresta",
      selfUri: this.getPath("/13"),
    },
    {
      id: 14,
      name: "La Boca",
      selfUri: this.getPath("/14"),
    },
    {
      id: 15,
      name: "La Paternal",
      selfUri: this.getPath("/15"),
    },
    {
      id: 16,
      name: "Liniers",
      selfUri: this.getPath("/16"),
    },
    {
      id: 17,
      name: "Mataderos",
      selfUri: this.getPath("/17"),
    },
    {
      id: 18,
      name: "Monte Castro",
      selfUri: this.getPath("/18"),
    },
    {
      id: 19,
      name: "Montserrat",
      selfUri: this.getPath("/19"),
    },
    {
      id: 20,
      name: "Nueva Pompeya",
      selfUri: this.getPath("/20"),
    },
    {
      id: 21,
      name: "Núñez",
      selfUri: this.getPath("/21"),
    },
    {
      id: 22,
      name: "Palermo",
      selfUri: this.getPath("/22"),
    },
    {
      id: 23,
      name: "Parque Avellaneda",
      selfUri: this.getPath("/23"),
    },
    {
      id: 24,
      name: "Parque Chacabuco",
      selfUri: this.getPath("/24"),
    },
    {
      id: 25,
      name: "Parque Chas",
      selfUri: this.getPath("/25"),
    },
    {
      id: 26,
      name: "Parque Patricios",
      selfUri: this.getPath("/26"),
    },
    {
      id: 27,
      name: "Puerto Madero",
      selfUri: this.getPath("/27"),
    },
    {
      id: 28,
      name: "Recoleta",
      selfUri: this.getPath("/28"),
    },
    {
      id: 29,
      name: "Retiro",
      selfUri: this.getPath("/29"),
    },
    {
      id: 30,
      name: "Saavedra",
      selfUri: this.getPath("/30"),
    },
    {
      id: 31,
      name: "San Cristóbal",
      selfUri: this.getPath("/31"),
    },
    {
      id: 32,
      name: "San Nicolás",
      selfUri: this.getPath("/32"),
    },
    {
      id: 33,
      name: "San Telmo",
      selfUri: this.getPath("/33"),
    },
    {
      id: 34,
      name: "Versalles",
      selfUri: this.getPath("/34"),
    },
    {
      id: 35,
      name: "Villa Crespo",
      selfUri: this.getPath("/35"),
    },
    {
      id: 36,
      name: "Villa Devoto",
      selfUri: this.getPath("/36"),
    },
    {
      id: 37,
      name: "Villa General Mitre",
      selfUri: this.getPath("/37"),
    },
    {
      id: 38,
      name: "Villa Lugano",
      selfUri: this.getPath("/38"),
    },
    {
      id: 39,
      name: "Villa Luro",
      selfUri: this.getPath("/39"),
    },
    {
      id: 40,
      name: "Villa Ortúzar",
      selfUri: this.getPath("/40"),
    },
    {
      id: 41,
      name: "Villa Pueyrredón",
      selfUri: this.getPath("/41"),
    },
    {
      id: 42,
      name: "Villa Real",
      selfUri: this.getPath("/42"),
    },
    {
      id: 43,
      name: "Villa Riachuelo",
      selfUri: this.getPath("/43"),
    },
    {
      id: 44,
      name: "Villa Santa Rita",
      selfUri: this.getPath("/44"),
    },
    {
      id: 45,
      name: "Villa Soldati",
      selfUri: this.getPath("/45"),
    },
    {
      id: 46,
      name: "Villa Urquiza",
      selfUri: this.getPath("/46"),
    },
    {
      id: 47,
      name: "Villa del Parque",
      selfUri: this.getPath("/47"),
    },
    {
      id: 48,
      name: "Vélez Sársfield",
      selfUri: this.getPath("/48"),
    },
  ];

  protected static getPath(path: string): string {
    return super.getPath(`${this.CITIES_PATH}${path}`);
  }

  public static getCityByIdProp(id: number) {
    return this.CITIES.find((city) => city.id === id) || this.CITIES[0];
  }

  public static getCityByUriProp(uri: string) {
    return this.CITIES.find((city) => city.selfUri === uri) || this.CITIES[0];
  }

  public static getAllCities() {
    return this.get("/cities", () =>
      this.jsonResponse(this.CITIES, { status: this.OK_STATUS })
    );
  }
  public static getCity() {
    return this.get("/cities/:cityId", () =>
      this.jsonResponse(this.getCityByIdProp(28), { status: this.OK_STATUS })
    );
  }
}

export default CityMock;
