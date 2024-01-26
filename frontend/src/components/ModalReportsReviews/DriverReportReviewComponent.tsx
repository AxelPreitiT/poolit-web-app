import styles from "./styles.module.scss";
import CircleImg from "@/components/img/circleImg/CircleImg.tsx";
import {Button} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import userPublicModel from "@/models/UserPublicModel.ts";

export interface DriverReportReviewComponentProps {
    selectDriver: (user:userPublicModel) => void;
    driver: userPublicModel
}

const DriverReportReviewComponent = ({driver, selectDriver}: DriverReportReviewComponentProps) => {
    const { t } = useTranslation();

    return (
        <div className={styles.marginCointainer}>
            <Button onClick={() => selectDriver(driver)} className={styles.userContainer}>
                <CircleImg src={driver.imageUri} size={50} />
                <div className={styles.infoContainer}>
                    <h4>
                        {t("format.name", {
                        name: driver.username,
                        surname: driver.surname})}
                    </h4>
                </div>
            </Button>
        </div>
    );
};


export default DriverReportReviewComponent;
