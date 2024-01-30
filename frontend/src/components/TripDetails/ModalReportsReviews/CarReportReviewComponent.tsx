import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import carModel from "@/models/CarModel.ts";
import tripModel from "@/models/TripModel.ts";

export interface CarReportReviewComponentProps {
    car: carModel;
    trip: tripModel;
    reporting: boolean;
}

const CarReportReviewComponent = ({trip, car, reporting}: CarReportReviewComponentProps) => {

    return (
        <div className={styles.marginCointainer}>
            <Button onClick={() => console.log(reporting)} className={styles.userContainer}>
                <CircleImg src={car.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>{trip.tripStatus}</h4>
                    <h4>{car.infoCar}</h4>
                </div>
            </Button>
        </div>
    );
};


export default CarReportReviewComponent;
