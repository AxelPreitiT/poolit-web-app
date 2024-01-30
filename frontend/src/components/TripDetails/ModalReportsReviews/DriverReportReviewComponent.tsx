import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import userPublicModel from "@/models/UserPublicModel.ts";
import tripModel from "@/models/TripModel.ts";
import useReviewsReportsDriver from "@/hooks/reportReview/useReviewsReportsDriver.tsx";
import {ReactNode} from "react";
import ReviewForm from "@/components/TripDetails/ModalsForms/ReviewForm.tsx";
import ReportForm from "@/components/TripDetails/ModalsForms/ReportForm.tsx";

export interface DriverReportReviewComponentProps {
    driver: userPublicModel
    reporting: boolean;
    trip: tripModel;
    openModalMake: (user: userPublicModel, reporting:boolean, form:ReactNode) => void;
}

const DriverReportReviewComponent = ({driver, reporting, trip, openModalMake}: DriverReportReviewComponentProps) => {
    const { t } = useTranslation();

    const {data:isReviewed, isLoading:isLoadingReview} = useReviewsReportsDriver(reporting, trip);


    const buttonStyle = {
        backgroundColor: isReviewed ? "green" : "orange",
    };

    return (
        (!isLoadingReview &&
        <div className={styles.marginCointainer}>
            <Button onClick={() => openModalMake(driver, reporting, reporting? <ReportForm/> : <ReviewForm/>)}
                    disabled={isReviewed}
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
                    <span>{t('trip_detail.review.driver_reported')}</span>
                </div>}
            {isReviewed && !reporting &&
                <div className={styles.aclaration_text}>
                    <span>{t('trip_detail.review.driver_reviewed')}</span>
                </div>}
        </div>)
    );
};


export default DriverReportReviewComponent;
