import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import {Button, Modal} from "react-bootstrap";
import Status from "@/enums/Status.ts";
import {useState} from "react";
import ModalReport from "@/components/ModalReportsReviews/ModalReport.tsx";
import PassangerModel from "@/models/PassangerModel.ts";
import ModalReview from "@/components/ModalReportsReviews/ModalReview.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import carModel from "@/models/CarModel.ts";
import ModalMakeReport from "@/components/ModalReportsReviews/ModalMakeReport.tsx";

interface RightDetailsProps {
    isPassanger: boolean;
    isDriver: boolean;
    status: string;
    passangers: PassangerModel[];
    driver: userPublicModel;
    car: carModel
}

const RightDetails = ({ isPassanger, isDriver, status, passangers, driver }: RightDetailsProps) => {
    const { t } = useTranslation();
    const [showModalReport, setModalReport] = useState(false);
    const [showModalMakeReport, setModalMakeReport] = useState(false);
    const [showModalReview, setModalReview] = useState(false);
    const [userReviewReport, setuserReviewReport] = useState<userPublicModel| null>(null);

    const openModalReport = () => {setModalReport(true);};
    const closeModalReport = () => {setModalReport(false);};
    const selectUser = (user:userPublicModel) => {
        setModalReport(false);
        setuserReviewReport(user);
        setModalMakeReport(true);
    };
    const closeModalMakeReport = () => {setModalMakeReport(false);};


    const openModalReview = () => {setModalReview(true);};
    const closeModalReview = () => {setModalReview(false);};

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
                            <Button className={styles.btn_join} onClick={openModalReview}>
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
                            <span onClick={openModalReport} style={{cursor: 'pointer', color:'blue'}}><i className="bi bi-car-front-fill"></i>{t('trip_detail.report.link_text')}</span>
                        </div>

                        <Modal show={showModalReport} onHide={closeModalReport} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReport closeModal={closeModalReport} selectUser={selectUser} passangers={passangers} driver={driver} isDriver={isDriver}/>
                        </Modal>

                        <Modal show={showModalReview} onHide={closeModalReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReview closeModal={closeModalReview} passangers={passangers} driver={driver} isDriver={isDriver}/>
                        </Modal>

                        <Modal show={showModalMakeReport} onHide={closeModalMakeReport} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalMakeReport closeModal={closeModalMakeReport} user={userReviewReport}/>
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