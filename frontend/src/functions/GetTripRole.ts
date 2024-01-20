import UserPrivateModel from "@/models/UserPrivateModel.ts";
import UserPublicModel from "@/models/UserPublicModel.ts";
import PassangerModel from "@/models/PassangerModel.ts";

const getTripRole = (user: UserPrivateModel, driver: UserPublicModel, passangers: PassangerModel[]): { isPassanger: boolean, isDriver: boolean } => {
    if (user.selfUri === driver.selfUri) {
        return { isPassanger: false, isDriver: true }
    }

    let isPassanger = false;
    for (const passanger of passangers) {
        if (passanger.userUri === user.selfUri) {
            isPassanger = true;
            break;
        }
    }

    return { isPassanger, isDriver: false };
};

export default getTripRole;