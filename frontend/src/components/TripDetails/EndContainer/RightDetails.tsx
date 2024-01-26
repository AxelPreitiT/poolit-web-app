import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import {Button, Modal} from "react-bootstrap";
import Status from "@/enums/Status.ts";
import {useState} from "react";
import ModalReport from "@/components/ModalReportsReviews/ModalReport.tsx";
import PassangerModel from "@/models/PassangerModel.ts";

interface RightDetailsProps {
    isPassanger: boolean;
    isDriver: boolean;
    status: string;
    passangers: PassangerModel[];
}

const RightDetails = ({ isPassanger, isDriver, status, passangers }: RightDetailsProps) => {
    const { t } = useTranslation();
    const [showModal, setShowModal] = useState(false);

    const openModal = () => {
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
    };

    const handleSpanClick = () => {
        // Lógica para abrir el modal cuando se hace clic en el span
        openModal();
    };
    return (
        (!isPassanger && !isDriver) ?
            <div className={styles.btn_container}>
                <Button className={styles.btn_join}>
                    <div className={styles.create_trip_btn}>
                        <i className="bi bi-check-lg light-text"></i>
                        <span>{t("trip_detail.btn.join")}</span>
                    </div>
                </Button>
            </div> :
            (status === Status.FINISHED ?
                    <div className={styles.review_btn}>
                        <div className={styles.btn_container}>
                            <Button className={styles.btn_join}>
                                <div className={styles.create_trip_btn}>
                                    <i className="bi bi-pencil-square"></i>
                                    <span>{t("trip_detail.btn.reviews")}</span>
                                </div>
                            </Button>
                            <Button className={styles.btn_trips}>
                                <div className={styles.create_trip_btn}>
                                    <i className="bi bi-car-front-fill light-text"></i>
                                    <span>{t("trip_detail.btn.my_trips")}</span>
                                </div>
                            </Button>
                        </div>
                        <div className={styles.report_link}>
                            <span>{t('trip_detail.report.pre_link_text')}</span>
                            <span onClick={handleSpanClick} style={{cursor: 'pointer', color:'blue'}}><i className="bi bi-car-front-fill"></i>{t('trip_detail.report.link_text')}</span>
                        </div>

                        <Modal show={showModal} onHide={closeModal} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReport closeModal={closeModal} passangers={passangers}/>
                        </Modal>
                    </div>
 :
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
                                        <span>{t("trip_detail.btn.delete")}</span>
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