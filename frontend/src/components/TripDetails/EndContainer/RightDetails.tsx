import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import {Button, Modal} from "react-bootstrap";
import Status from "@/enums/Status.ts";
import {useState} from "react";
import userPublicModel from "@/models/UserPublicModel.ts";
import carModel from "@/models/CarModel.ts";
import {Link} from "react-router-dom";
import {createdTripsPath, reservedTripsPath} from "@/AppRouter.tsx";
import tripModel from "@/models/TripModel.ts";
import passangerModel from "@/models/PassangerModel.ts";
import passangerStatus from "@/enums/PassangerStatus.ts";
import ModalReport from "@/components/TripDetails/ModalsRR/ModalReport.tsx";

interface RightDetailsProps {
    isPassanger: boolean;
    isDriver: boolean;
    trip: tripModel;
    passanger: passangerModel | undefined;
    status: string;
    driver: userPublicModel;
    car: carModel;
}

const RightDetails = ({ isPassanger, isDriver, trip, passanger, status,  driver , car}: RightDetailsProps) => {

    const { t } = useTranslation();
    const [showModalReport, setModalReport] = useState(false);

    const openModalReport = () => {setModalReport(true);};
    const closeModalReport = () => {setModalReport(false);};

    const [showModalReview, setModalReview] = useState(false);

    const openModalReview = () => {setModalReview(true);};
    const closeModalReview = () => {
        setModalReview(false);
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
                ( isDriver || passanger?.passengerState == passangerStatus.ACCEPTED ?
                    <div className={styles.review_btn}>
                        <div className={styles.btn_container}>
                            <Button className={styles.btn_join} onClick={openModalReview}>
                                <div className={styles.create_trip_btn}>
                                    <i className="bi bi-pencil-square"></i>
                                    <span>{t("trip_detail.btn.reviews")}</span>
                                </div>
                            </Button>
                            <Link to={isDriver? createdTripsPath : reservedTripsPath} >
                                <Button className={styles.btn_trips}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-car-front-fill light-text"></i>
                                        <span>{t("trip_detail.btn.my_trips")}</span>
                                    </div>
                                </Button>
                            </Link>
                        </div>
                        <div className={styles.report_link}>
                            <span>{t('trip_detail.report.pre_link_text')}</span>
                            <span onClick={openModalReport} style={{cursor: 'pointer', color:'blue'}}><i className="bi bi-car-front-fill"></i>{t('trip_detail.report.link_text')}</span>
                        </div>

                        <Modal show={showModalReport} onHide={closeModalReport} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReport closeModal={closeModalReport} driver={driver} isDriver={isDriver} trip={trip} passanger={passanger} reporting={true}/>
                        </Modal>

                        <Modal show={showModalReview} onHide={closeModalReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReport closeModal={closeModalReview} driver={driver} isDriver={isDriver} trip={trip} passanger={passanger} car={car} reporting={false}/>
                        </Modal>


                    </div> :
                    <div className={styles.btn_container}>
                        <Link to={reservedTripsPath} >
                            <Button className={styles.btn_trips}>
                                <div className={styles.create_trip_btn}>
                                    <i className="bi bi-car-front-fill light-text"></i>
                                    <span>{t("trip_detail.btn.my_trips")}</span>
                                </div>
                            </Button>
                        </Link>
                    </div>
                )
 :
                    (isDriver ?
                        <div className={styles.btn_container}>
                            <Link to={createdTripsPath} >
                                <Button className={styles.btn_trips}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-car-front-fill light-text"></i>
                                        <span>{t("trip_detail.btn.my_trips")}</span>
                                    </div>
                                </Button>
                            </Link>
                                <Button className={styles.btn_cancel}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-x light-text"></i>
                                        <span>{t("trip_detail.btn.delete")}</span>
                                    </div>
                                </Button>
                        </div> :
                        <div className={styles.btn_container}>
                            <Link to={reservedTripsPath} >
                                <Button className={styles.btn_trips}>
                                    <div className={styles.create_trip_btn}>
                                        <i className="bi bi-car-front-fill light-text"></i>
                                        <span>{t("trip_detail.btn.my_trips")}</span>
                                    </div>
                                </Button>
                            </Link>
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