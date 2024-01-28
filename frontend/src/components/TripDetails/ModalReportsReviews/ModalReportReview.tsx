import styles from "./styles.module.scss";
import {Button, Modal} from "react-bootstrap";
import DriverReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/DriverReportReviewComponent.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import {useTranslation} from "react-i18next";
import EmptyList from "@/components/emptyList/EmptyList.tsx";
import carModel from "@/models/CarModel.ts";
import CarReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/CarReportReviewComponent.tsx";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import {INITIALPAGE, PASSANGERPAGESIZE} from "@/enums/PaginationConstants.ts";
import PassangerComponent from "@/components/passanger/Passanger.tsx";
import usePassangerByUri from "@/hooks/passanger/usePassangerByUri.tsx";
import {parseTemplate} from "url-template";
import {useLocation, useSearchParams} from "react-router-dom";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
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

const ModalReportReview = ({ closeModal, driver, car, isDriver, trip, passanger , selectUser, selectCar}: ModalReportProps) => {
    const { t } = useTranslation();
    const [params] = useSearchParams();
    const { search } = useLocation();
    const page = new URLSearchParams(search).get("page");
    const currentPage = page == null ? INITIALPAGE : parseInt(page, 10);

    //const {isLoading, passangers} = useGetPassangers(isDriver , isPassanger, params, currentPassanger, trip);

    const startDateTime = params.get("startDateTime") || "";
    const endDateTime = params.get("endDateTime") || "";
    const uri = parseTemplate(trip?.passengersUriTemplate as string).expand({
        userId: null,
        startDateTime: startDateTime,
        endDateTime: endDateTime,
        passengerState: "ACCEPTED",
    });

    const finalUri = isDriver? uri : passanger?.otherPassengersUri as string;

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
                        <DriverReportReviewComponent driver={driver} selectDriver={selectUser}/>
                    </div>}
                    {!isDriver && car != null && selectCar!= null &&
                    <div className={styles.passangerContainer}>
                        <div className={styles.titleContainer}>
                            <i className="bi bi-car-front-fill h3"></i>
                            <h3>{t('modal.car')}</h3>
                        </div>
                        <CarReportReviewComponent car={car} selectCar={selectCar}/>
                    </div>}
                    <PaginationComponent
                        empty_component={
                            <div className={styles.review_empty_container}>
                                <i className={`bi-solid bi-people h2`}></i>
                                <h3 className="italic-text placeholder-text">
                                    {t("trip_detail.passengers.empty")}
                                </h3>
                            </div>
                        }
                        uri={createPaginationUri(finalUri, currentPage, PASSANGERPAGESIZE)}
                        current_page={currentPage}
                        component_name={PassangerComponent}
                        useFuction={usePassangerByUri}
                        itemsName={t("trip_detail.passengers.header")}
                    />
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

export default ModalReportReview;
