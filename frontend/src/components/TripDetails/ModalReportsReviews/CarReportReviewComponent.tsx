import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import carModel from "@/models/CarModel.ts";

export interface CarReportReviewComponentProps {
    selectCar: () => void;
    car: carModel
}

const CarReportReviewComponent = ({car, selectCar}: CarReportReviewComponentProps) => {

    return (
        <div className={styles.marginCointainer}>
            <Button onClick={() => selectCar()} className={styles.userContainer}>
                <CircleImg src={car.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>{car.infoCar}</h4>
                </div>
            </Button>
        </div>
    );
};


export default CarReportReviewComponent;
