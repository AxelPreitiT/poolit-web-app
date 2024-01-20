import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Button } from "react-bootstrap";

interface RightDetailsProps {
    isPassanger: boolean;
    isDriver: boolean;
    status: string;
}

const RightDetails = ({ isPassanger, isDriver, status }: RightDetailsProps) => {
    const { t } = useTranslation();

    return (
        (!isPassanger && !isDriver) ?
            <div className={styles.btn_container}>
                <Button className={styles.btn_trips}>
                    <div className={styles.create_trip_btn}>
                        <i className="bi bi-car-front-fill light-text"></i>
                        <span>{t("trip_detail.btn.my_trips")}</span>
                    </div>
                </Button>
            </div> :
            (status === "FISINSHED" ?
                    <div className={styles.btn_container}>
                        <Button className={styles.btn_trips}>
                            <div className={styles.create_trip_btn}>
                                <i className="bi bi-car-front-fill light-text"></i>
                                <span>{t("trip_detail.btn.my_trips")}</span>
                            </div>
                        </Button>
                        <Button className={styles.btn_cancel}>
                            <div className={styles.create_trip_btn}>
                                <i className="bi bi-x light-text"></i>
                                <span>{t("trip_detail.btn.cancel")}</span>
                            </div>
                        </Button>
                    </div> :
                    (isDriver ?
                            <div className={styles.btn_container}>
                                <Button className={styles.btn_trips}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-car-front-fill light-text"></i>
                                        <span>{t("trip_detail.btn.my_trips")}</span>
                                    </div>
                                </Button>
                                <Button className={styles.btn_cancel}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-x light-text"></i>
                                        <span>{t("trip_detail.btn.cancel")}</span>
                                    </div>
                                </Button>
                            </div> :
                            <div className={styles.btn_container}>
                                <Button className={styles.btn_trips}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-car-front-fill light-text"></i>
                                        <span>{t("trip_detail.btn.my_trips")}</span>
                                    </div>
                                </Button>
                                <Button className={styles.btn_cancel}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-x light-text"></i>
                                        <span>{t("trip_detail.btn.cancel")}</span>
                                    </div>
                                </Button>
                            </div>
                    )
            )
    );
};

export default RightDetails;