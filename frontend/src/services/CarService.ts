import Service from "@/services/Service.ts";
import CarModel from "@/models/CarModel.ts";
import CarApi from "@/api/CarApi.ts";

class CarService extends Service {

    public static getCarsByUserId = async (selftUri : string): Promise<CarModel[]> => {
        const pathArray = new URL(selftUri).pathname.split("/");
        const id = pathArray[pathArray.length - 1];
        const uri = `http://localhost:8080/paw-2023a-07/api/cars?fromUser=${id}`
        return await this.resolveQuery(CarApi.getCarsByUser(uri));
    };

}

export default CarService;
