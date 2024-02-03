import BaseMock from "@/__test__/mocks/BaseMock.ts";
import cityModel from "@/models/CityModel.ts";

const cityList: cityModel[] = [
    {
        "id": 1,
        "name": "Agronomía",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/1"
    },
    {
        "id": 2,
        "name": "Almagro",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/2"
    },
    {
        "id": 3,
        "name": "Balvanera",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/3"
    },
    {
        "id": 4,
        "name": "Barracas",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/4"
    },
    {
        "id": 5,
        "name": "Belgrano",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/5"
    },
    {
        "id": 6,
        "name": "Boedo",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/6"
    },
    {
        "id": 7,
        "name": "Caballito",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/7"
    },
    {
        "id": 8,
        "name": "Chacarita",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/8"
    },
    {
        "id": 9,
        "name": "Coghlan",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/9"
    },
    {
        "id": 10,
        "name": "Colegiales",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/10"
    },
    {
        "id": 11,
        "name": "Constitución",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/11"
    },
    {
        "id": 12,
        "name": "Flores",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/12"
    },
    {
        "id": 13,
        "name": "Floresta",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/13"
    },
    {
        "id": 14,
        "name": "La Boca",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/14"
    },
    {
        "id": 15,
        "name": "La Paternal",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/15"
    },
    {
        "id": 16,
        "name": "Liniers",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/16"
    },
    {
        "id": 17,
        "name": "Mataderos",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/17"
    },
    {
        "id": 18,
        "name": "Monte Castro",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/18"
    },
    {
        "id": 19,
        "name": "Montserrat",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/19"
    },
    {
        "id": 20,
        "name": "Nueva Pompeya",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/20"
    },
    {
        "id": 21,
        "name": "Núñez",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/21"
    },
    {
        "id": 22,
        "name": "Palermo",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/22"
    },
    {
        "id": 23,
        "name": "Parque Avellaneda",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/23"
    },
    {
        "id": 24,
        "name": "Parque Chacabuco",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/24"
    },
    {
        "id": 25,
        "name": "Parque Chas",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/25"
    },
    {
        "id": 26,
        "name": "Parque Patricios",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/26"
    },
    {
        "id": 27,
        "name": "Puerto Madero",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/27"
    },
    {
        "id": 28,
        "name": "Recoleta",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/28"
    },
    {
        "id": 29,
        "name": "Retiro",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/29"
    },
    {
        "id": 30,
        "name": "Saavedra",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/30"
    },
    {
        "id": 31,
        "name": "San Cristóbal",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/31"
    },
    {
        "id": 32,
        "name": "San Nicolás",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/32"
    },
    {
        "id": 33,
        "name": "San Telmo",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/33"
    },
    {
        "id": 34,
        "name": "Versalles",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/34"
    },
    {
        "id": 35,
        "name": "Villa Crespo",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/35"
    },
    {
        "id": 36,
        "name": "Villa Devoto",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/36"
    },
    {
        "id": 37,
        "name": "Villa General Mitre",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/37"
    },
    {
        "id": 38,
        "name": "Villa Lugano",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/38"
    },
    {
        "id": 39,
        "name": "Villa Luro",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/39"
    },
    {
        "id": 40,
        "name": "Villa Ortúzar",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/40"
    },
    {
        "id": 41,
        "name": "Villa Pueyrredón",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/41"
    },
    {
        "id": 42,
        "name": "Villa Real",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/42"
    },
    {
        "id": 43,
        "name": "Villa Riachuelo",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/43"
    },
    {
        "id": 44,
        "name": "Villa Santa Rita",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/44"
    },
    {
        "id": 45,
        "name": "Villa Soldati",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/45"
    },
    {
        "id": 46,
        "name": "Villa Urquiza",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/46"
    },
    {
        "id": 47,
        "name": "Villa del Parque",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/47"
    },
    {
        "id": 48,
        "name": "Vélez Sársfield",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/48"
    }
]

const city: cityModel = {
        "id": 28,
        "name": "Recoleta",
        "selfUri": "http://localhost:8080/paw-2023a-07/api/cities/28"
    };

class CityMock extends BaseMock{
    public static getAllCities(){
        return this.get(this.getPath('/cities'),()=>{
            this.jsonResponse(cityList,{status:this.OK_STATUS})
        })
    }
    public static getCity(){
        return this.get(this.getPath("/cities/:cityId"),()=>{
            this.jsonResponse(city,{status:this.OK_STATUS})
        })
    }
}

export default CityMock;