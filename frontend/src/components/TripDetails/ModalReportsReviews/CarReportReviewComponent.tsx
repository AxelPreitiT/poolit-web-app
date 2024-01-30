import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import carModel from "@/models/CarModel.ts";
import tripModel from "@/models/TripModel.ts";
import useReviewsCar from "@/hooks/reportReview/useReviewsCar.tsx";

export interface CarReportReviewComponentProps {
    car: carModel;
    trip: tripModel;
    openModalCar: () => void;
}

const CarReportReviewComponent = ({trip, car, openModalCar}: CarReportReviewComponentProps) => {

    const {data:isReviewed, isLoading:isLoadingReview} = useReviewsCar(trip);

    const buttonStyle = {
        backgroundColor: isReviewed ? "green" : "orange",
    };

    return (
        (!isLoadingReview &&
        <div className={styles.marginCointainer}>
            <Button onClick={() => openModalCar}
                    className={styles.userContainer}
                    style={buttonStyle}>
                <CircleImg src={car.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>{trip.tripStatus}</h4>
                    <h4>{car.infoCar}</h4>
                </div>
            </Button>
            {isReviewed &&
                <div className={styles.aclaration_text}>
                    <span>AUTO RESEÑADO</span>
                </div>}
        </div>)
    );
};


export default CarReportReviewComponent;
