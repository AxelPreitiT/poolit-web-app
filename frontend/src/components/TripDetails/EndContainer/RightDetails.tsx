import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import {Button, Modal} from "react-bootstrap";
import Status from "@/enums/Status.ts";
import {useState} from "react";
import ModalReportReview from "@/components/ModalReportsReviews/ModalReportReview.tsx";
import PassangerModel from "@/models/PassangerModel.ts";
import userPublicModel from "@/models/UserPublicModel.ts";
import carModel from "@/models/CarModel.ts";
import ModalMakeReportReview from "@/components/ModalReportsReviews/ModalMakeReportReview.tsx";
import ModalCarMakeReview from "@/components/ModalReportsReviews/ModalCarMakeReview.tsx";

interface RightDetailsProps {
    isPassanger: boolean;
    isDriver: boolean;
    status: string;
    passangers: PassangerModel[];
    driver: userPublicModel;
    car: carModel
}

const RightDetails = ({ isPassanger, isDriver, status, passangers, driver , car}: RightDetailsProps) => {
    const { t } = useTranslation();
    const [showModalReport, setModalReport] = useState(false);
    const [showModalMakeReport, setModalMakeReport] = useState(false);

    const [showModalReview, setModalReview] = useState(false);
    const [showModalMakeReview, setModalMakeReview] = useState(false);
    const [showModalCarMakeReview, setModalCarMakeReview] = useState(false);


    const [userReviewReport, setuserReviewReport] = useState<userPublicModel| null>(null);

    const openModalReport = () => {setModalReport(true);};
    const closeModalReport = () => {setModalReport(false);};
    const selectUserReport = (user:userPublicModel) => {
        setModalReport(false);
        setuserReviewReport(user);
        setModalMakeReport(true);
    };
    const closeModalMakeReport = () => {setModalMakeReport(false);};


    const openModalReview = () => {setModalReview(true);};
    const closeModalReview = () => {setModalReview(false);};
    const selectUserReview = (user:userPublicModel) => {
        setModalReview(false);
        setuserReviewReport(user);
        setModalMakeReview(true);
    };
    const selectCarReview = () => {
        setModalReview(false);
        setModalCarMakeReview(true);
    };
    const closeModalMakeReview = () => {setModalMakeReview(false);};
    const closeModalCarReview = () => {setModalCarMakeReview(false);};

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
                            <ModalReportReview closeModal={closeModalReport} selectUser={selectUserReport} passangers={passangers} driver={driver} isDriver={isDriver}/>
                        </Modal>

                        <Modal show={showModalMakeReport} onHide={closeModalMakeReport} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalMakeReportReview closeModal={closeModalMakeReport} user={userReviewReport}/>
                        </Modal>

                        <Modal show={showModalReview} onHide={closeModalReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReportReview closeModal={closeModalReview} selectUser={selectUserReview} passangers={passangers} driver={driver} isDriver={isDriver} car={car} selectCar={selectCarReview}/>
                        </Modal>

                        <Modal show={showModalMakeReview} onHide={closeModalMakeReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalMakeReportReview closeModal={closeModalMakeReview} user={userReviewReport}/>
                        </Modal>

                        <Modal show={showModalCarMakeReview} onHide={closeModalCarReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalCarMakeReview closeModal={closeModalCarReview} car={car}/>
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