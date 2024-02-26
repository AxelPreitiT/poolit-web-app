import passangerModel from "@/models/PassangerModel.ts";
import ReserveStatus from "@/enums/ReserveStatus.ts";

function getStatusPassanger(passanger: passangerModel) : ReserveStatus {
    const currentDate = new Date();
    const startDate = new Date(passanger.startDateTime);
    const endDate = new Date(passanger.endDateTime);

    if (currentDate < startDate){
        return ReserveStatus.NOT_STARTED;
    } else if (currentDate >= startDate && currentDate <= endDate) {
        return ReserveStatus.STARTED;
    } else {
        return ReserveStatus.FINISHED;
    }

}

export default getStatusPassanger;