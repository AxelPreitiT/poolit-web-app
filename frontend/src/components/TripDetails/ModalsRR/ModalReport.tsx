import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import DriverReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/DriverReportReviewComponent.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import {useTranslation} from "react-i18next";
import EmptyList from "@/components/emptyList/EmptyList.tsx";
import carModel from "@/models/CarModel.ts";
import CarReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/CarReportReviewComponent.tsx";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import PassangerReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/PassangerReportReviewComponent.tsx";
import getUriPassangers from "@/functions/getUriPassangers.tsx";
import PaginationComponentExtraData from "@/components/pagination/PaginationComponent/PaginationComponentExtraData.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import {INITIALPAGE, PASSANGERPAGESIZE} from "@/enums/PaginationConstants.ts";
import usePassangerByUri from "@/hooks/passanger/usePassangerByUri.tsx";

export interface ModalReportProps {
    closeModal: () => void;
    driver: userPublicModel;
    isDriver: boolean;
    trip: tripModel;
    passanger: passangerModel | undefined;
    car?: carModel;
    reporting: boolean;
}

const ModalReport = ({ closeModal, driver, car, isDriver, trip, passanger, reporting}: ModalReportProps) => {
    const { t } = useTranslation();
    const uri = getUriPassangers(isDriver, passanger, trip);

    return (
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
                                <DriverReportReviewComponent driver={driver} trip={trip} reporting={reporting}/>
                            </div>}
                        {!isDriver && car != null &&
                            <div className={styles.passangerContainer}>
                                <div className={styles.titleContainer}>
                                    <i className="bi bi-car-front-fill h3"></i>
                                    <h3>{t('modal.car')}</h3>
                                </div>
                                <CarReportReviewComponent car={car} trip={trip}/>
                            </div>}
                            <div className={styles.passangerContainer}>
                                <div className={styles.titleContainer}>
                                    <i className="bi bi-people-fill h3"></i>
                                    <h3>{t('modal.passangers')}</h3>
                                </div>
                                <PaginationComponentExtraData
                                    CardComponent={PassangerReportReviewComponent}
                                    extraData={reporting}
                                    uri={createPaginationUri(uri, INITIALPAGE, PASSANGERPAGESIZE)}
                                    current_page={INITIALPAGE}
                                    useFuction={usePassangerByUri}
                                    empty_component={
                                        <div className={styles.review_empty_container}>
                                            <i className={`bi-solid bi-people h2`}></i>
                                            <h3 className="italic-text placeholder-text">
                                                {t("trip_detail.passengers.empty")}
                                            </h3>
                                        </div>
                                    }
                                    itemsName={t("trip_detail.passengers.header")}
                                />
                            </div>
                        {isDriver && car == null &&
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
            </div>
    );
};

export default ModalReport;
