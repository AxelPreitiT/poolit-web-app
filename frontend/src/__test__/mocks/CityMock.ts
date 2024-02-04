import BaseMock from "@/__test__/mocks/BaseMock.ts";
import CityModel from "@/models/CityModel.ts";

class CityMock extends BaseMock {
  public static CITIES: CityModel[] = [
    {
      id: 1,
      name: "Agronomía",
      selfUri: this.getPath("/cities/1"),
    },
    {
      id: 2,
      name: "Almagro",
      selfUri: this.getPath("/cities/2"),
    },
    {
      id: 3,
      name: "Balvanera",
      selfUri: this.getPath("/cities/3"),
    },
    {
      id: 4,
      name: "Barracas",
      selfUri: this.getPath("/cities/4"),
    },
    {
      id: 5,
      name: "Belgrano",
      selfUri: this.getPath("/cities/5"),
    },
    {
      id: 6,
      name: "Boedo",
      selfUri: this.getPath("/cities/6"),
    },
    {
      id: 7,
      name: "Caballito",
      selfUri: this.getPath("/cities/7"),
    },
    {
      id: 8,
      name: "Chacarita",
      selfUri: this.getPath("/cities/8"),
    },
    {
      id: 9,
      name: "Coghlan",
      selfUri: this.getPath("/cities/9"),
    },
    {
      id: 10,
      name: "Colegiales",
      selfUri: this.getPath("/cities/10"),
    },
    {
      id: 11,
      name: "Constitución",
      selfUri: this.getPath("/cities/11"),
    },
    {
      id: 12,
      name: "Flores",
      selfUri: this.getPath("/cities/12"),
    },
    {
      id: 13,
      name: "Floresta",
      selfUri: this.getPath("/cities/13"),
    },
    {
      id: 14,
      name: "La Boca",
      selfUri: this.getPath("/cities/14"),
    },
    {
      id: 15,
      name: "La Paternal",
      selfUri: this.getPath("/cities/15"),
    },
    {
      id: 16,
      name: "Liniers",
      selfUri: this.getPath("/cities/16"),
    },
    {
      id: 17,
      name: "Mataderos",
      selfUri: this.getPath("/cities/17"),
    },
    {
      id: 18,
      name: "Monte Castro",
      selfUri: this.getPath("/cities/18"),
    },
    {
      id: 19,
      name: "Montserrat",
      selfUri: this.getPath("/cities/19"),
    },
    {
      id: 20,
      name: "Nueva Pompeya",
      selfUri: this.getPath("/cities/20"),
    },
    {
      id: 21,
      name: "Núñez",
      selfUri: this.getPath("/cities/21"),
    },
    {
      id: 22,
      name: "Palermo",
      selfUri: this.getPath("/cities/22"),
    },
    {
      id: 23,
      name: "Parque Avellaneda",
      selfUri: this.getPath("/cities/23"),
    },
    {
      id: 24,
      name: "Parque Chacabuco",
      selfUri: this.getPath("/cities/24"),
    },
    {
      id: 25,
      name: "Parque Chas",
      selfUri: this.getPath("/cities/25"),
    },
    {
      id: 26,
      name: "Parque Patricios",
      selfUri: this.getPath("/cities/26"),
    },
    {
      id: 27,
      name: "Puerto Madero",
      selfUri: this.getPath("/cities/27"),
    },
    {
      id: 28,
      name: "Recoleta",
      selfUri: this.getPath("/cities/28"),
    },
    {
      id: 29,
      name: "Retiro",
      selfUri: this.getPath("/cities/29"),
    },
    {
      id: 30,
      name: "Saavedra",
      selfUri: this.getPath("/cities/30"),
    },
    {
      id: 31,
      name: "San Cristóbal",
      selfUri: this.getPath("/cities/31"),
    },
    {
      id: 32,
      name: "San Nicolás",
      selfUri: this.getPath("/cities/32"),
    },
    {
      id: 33,
      name: "San Telmo",
      selfUri: this.getPath("/cities/33"),
    },
    {
      id: 34,
      name: "Versalles",
      selfUri: this.getPath("/cities/34"),
    },
    {
      id: 35,
      name: "Villa Crespo",
      selfUri: this.getPath("/cities/35"),
    },
    {
      id: 36,
      name: "Villa Devoto",
      selfUri: this.getPath("/cities/36"),
    },
    {
      id: 37,
      name: "Villa General Mitre",
      selfUri: this.getPath("/cities/37"),
    },
    {
      id: 38,
      name: "Villa Lugano",
      selfUri: this.getPath("/cities/38"),
    },
    {
      id: 39,
      name: "Villa Luro",
      selfUri: this.getPath("/cities/39"),
    },
    {
      id: 40,
      name: "Villa Ortúzar",
      selfUri: this.getPath("/cities/40"),
    },
    {
      id: 41,
      name: "Villa Pueyrredón",
      selfUri: this.getPath("/cities/41"),
    },
    {
      id: 42,
      name: "Villa Real",
      selfUri: this.getPath("/cities/42"),
    },
    {
      id: 43,
      name: "Villa Riachuelo",
      selfUri: this.getPath("/cities/43"),
    },
    {
      id: 44,
      name: "Villa Santa Rita",
      selfUri: this.getPath("/cities/44"),
    },
    {
      id: 45,
      name: "Villa Soldati",
      selfUri: this.getPath("/cities/45"),
    },
    {
      id: 46,
      name: "Villa Urquiza",
      selfUri: this.getPath("/cities/46"),
    },
    {
      id: 47,
      name: "Villa del Parque",
      selfUri: this.getPath("/cities/47"),
    },
    {
      id: 48,
      name: "Vélez Sársfield",
      selfUri: this.getPath("/cities/48"),
    },
  ];

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
