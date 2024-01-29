import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import userPublicModel from "@/models/UserPublicModel.ts";

export interface DriverReportReviewComponentProps {
    selectDriver: (user:userPublicModel) => void;
    driver: userPublicModel
    isReviewed: boolean;
}

const DriverReportReviewComponent = ({driver, selectDriver, isReviewed}: DriverReportReviewComponentProps) => {
    const { t } = useTranslation();

    const buttonStyle = {
        backgroundColor: isReviewed ? "green" : "orange",
    };

    return (
        <div className={styles.marginCointainer}>
            <Button onClick={() => selectDriver(driver)} disabled={isReviewed} style={buttonStyle} className={styles.userContainer}>
                <CircleImg src={driver.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>
                        {t("format.name", {
                        name: driver.username,
                        surname: driver.surname})}
                    </h4>
                </div>
            </Button>
            {isReviewed &&
                <div className={styles.aclaration_text}>
                    <span>conductor reseñado</span>
                </div>}
        </div>
    );
};


export default DriverReportReviewComponent;
