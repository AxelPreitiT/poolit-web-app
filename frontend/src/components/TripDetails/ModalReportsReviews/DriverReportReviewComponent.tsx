import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import userPublicModel from "@/models/UserPublicModel.ts";
import tripModel from "@/models/TripModel.ts";
import useReviewsReportsDriver from "@/hooks/passanger/useReviewsReportsDriver.tsx";

export interface DriverReportReviewComponentProps {
    driver: userPublicModel
    reporting: boolean;
    trip: tripModel
}

const DriverReportReviewComponent = ({driver, reporting, trip}: DriverReportReviewComponentProps) => {
    const { t } = useTranslation();

    const {data:isReviewed, isLoading:isLoadingReview} = useReviewsReportsDriver(!reporting, trip.driverReportsUriTemplate);


    const buttonStyle = {
        backgroundColor: isReviewed ? "green" : "orange",
    };

    return (
        (!isLoadingReview &&
        <div className={styles.marginCointainer}>
            <Button onClick={() => console.log("click")}
                    disabled={isReviewed && reporting}
                    style={buttonStyle}
                    className={styles.userContainer}>
                <CircleImg src={driver.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>
                        {t("format.name", {
                        name: driver.username,
                        surname: driver.surname})}
                    </h4>
                </div>
            </Button>
            {isReviewed && reporting &&
                <div className={styles.aclaration_text}>
                    <span>conductor reseñado</span>
                </div>}
        </div>)
    );
};


export default DriverReportReviewComponent;
