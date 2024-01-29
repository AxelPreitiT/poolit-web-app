import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import DriverReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/DriverReportReviewComponent.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import {useTranslation} from "react-i18next";
import EmptyList from "@/components/emptyList/EmptyList.tsx";
import carModel from "@/models/CarModel.ts";
import CarReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/CarReportReviewComponent.tsx";
//import {INITIALPAGE} from "@/enums/PaginationConstants.ts";
import { useSearchParams} from "react-router-dom";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import PassangerReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/PassangerReportReviewComponent.tsx";
import useGetPassangers from "@/hooks/passanger/useGetPassangers.tsx";
import useReviewsDriver from "@/hooks/passanger/useReviewsDriver.tsx";
import getUriPassangers from "@/functions/getUriPassangers.tsx";
//import useGetPassangers from "@/hooks/passanger/useGetPassangers.tsx";

export interface ModalReportProps {
    closeModal: () => void;
    driver: userPublicModel;
    isDriver: boolean;
    selectUser: (user:userPublicModel) => void;
    trip: tripModel;
    passanger: passangerModel | undefined;
    car?: carModel;
    selectCar?: () => void;
}

const ModalReport = ({ closeModal, driver, car, isDriver, trip, passanger, selectUser, selectCar}: ModalReportProps) => {
    const { t } = useTranslation();
    const {data:isReviewed, isLoading:isLoadingReview} = useReviewsDriver(trip.driverReviewsUriTemplate)

    const uri = getUriPassangers(isDriver, isDriver, passanger, trip);

    return (
        (!isLoadingReview && isReviewed!=undefined &&
            <div className={styles.propProfile}>
                <Modal.Header closeButton>
                    <Modal.Title><h2 className={styles.titleModal}>{t('modal.report.title')}</h2></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className={styles.categoryContainer}>
                        {!isDriver &&
                            <div className={styles.passangerContainer}>
                                <div className={styles.titleContainer}>
                                    <i className="bi bi-person-fill h3"></i>
                                    <h3>{t('modal.driver')}</h3>
                                </div>
                                <DriverReportReviewComponent driver={driver} selectDriver={selectUser} isReviewed={isReviewed}/>
                            </div>}
                        {!isDriver && car != null && selectCar!= null &&
                            <div className={styles.passangerContainer}>
                                <div className={styles.titleContainer}>
                                    <i className="bi bi-car-front-fill h3"></i>
                                    <h3>{t('modal.car')}</h3>
                                </div>
                                <CarReportReviewComponent car={car} selectCar={selectCar}/>
                            </div>}
                            <div className={styles.passangerContainer}>
                                <div className={styles.titleContainer}>
                                    <i className="bi bi-people-fill h3"></i>
                                    <h3>{t('modal.passangers')}</h3>
                                </div>

                                {passangers.map((item, index) => (
                                    <PassangerReportReviewComponent key={index} passanger={item} selectPassanger={selectUser}/>
                                ))}
                            </div>
                        {isDriver && passangers.length != 0 && car == null &&
                            <EmptyList
                                text={t("modal.report.empty")}
                                icon={"people-fill"}
                            />}
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button className={styles.backBtn} onClick={closeModal}>
                        {t('modal.close')}
                    </Button>
                </Modal.Footer>
            </div>)
    );
};

export default ModalReport;
