import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import {Button, Modal} from "react-bootstrap";
import Status from "@/enums/Status.ts";
import {useState} from "react";
import ModalReportReview from "@/components/TripDetails/ModalReportsReviews/ModalReportReview.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import carModel from "@/models/CarModel.ts";
import ModalMakeReportReview from "@/components/TripDetails/ModalReportsReviews/ModalMakeReportReview.tsx";
import ModalCarMakeReview from "@/components/TripDetails/ModalReportsReviews/ModalCarMakeReview.tsx";
import {Link} from "react-router-dom";
import {createdTripsPath, reservedTripsPath} from "@/AppRouter.tsx";
import ReportForm from "@/components/TripDetails/Modals/ReportForm.tsx";
import ReviewForm from "@/components/TripDetails/Modals/ReviewForm.tsx";
import ReviewCarForm from "@/components/TripDetails/Modals/ReviewCarForm.tsx";
import tripModel from "@/models/TripModel.ts";
import passangerModel from "@/models/PassangerModel.ts";
import passangerStatus from "@/enums/PassangerStatus.ts";

interface RightDetailsProps {
    isPassanger: boolean;
    isDriver: boolean;
    trip: tripModel;
    passanger: passangerModel | undefined;
    driver: userPublicModel;
    car: carModel;
}

const RightDetails = ({ isPassanger, isDriver, trip, passanger, driver , car}: RightDetailsProps) => {
    const startDate = new Date(trip.startDateTime)
    const currentDate = new Date();
    const endDate = new Date(trip.endDateTime)
    const getStatusByDates = (currentDate < startDate ? Status.NOT_STARTED : (currentDate > endDate ? Status.FINISHED : Status.IN_PROGRESS) )

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
            (getStatusByDates === Status.FINISHED ?
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
                            <ModalReportReview closeModal={closeModalReport} selectUser={selectUserReport} driver={driver} isDriver={isDriver} trip={trip} passanger={passanger}/>
                        </Modal>

                        <Modal show={showModalMakeReport} onHide={closeModalMakeReport} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalMakeReportReview closeModal={closeModalMakeReport} user={userReviewReport} reportForm={<ReportForm/>}/>
                        </Modal>

                        <Modal show={showModalReview} onHide={closeModalReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalReportReview closeModal={closeModalReview} selectUser={selectUserReview} driver={driver} isDriver={isDriver} trip={trip} passanger={passanger} car={car} selectCar={selectCarReview}/>
                        </Modal>

                        <Modal show={showModalMakeReview} onHide={closeModalMakeReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalMakeReportReview closeModal={closeModalMakeReview} user={userReviewReport} reportForm={<ReviewForm/>}/>
                        </Modal>

                        <Modal show={showModalCarMakeReview} onHide={closeModalCarReview} aria-labelledby="contained-modal-title-vcenter" centered>
                            <ModalCarMakeReview closeModal={closeModalCarReview} car={car} reportForm={<ReviewCarForm/>}/>
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