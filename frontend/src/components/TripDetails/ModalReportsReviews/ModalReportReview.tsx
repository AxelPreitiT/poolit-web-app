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
import PassangerReportReviewComponent
    from "@/components/TripDetails/ModalReportsReviews/PassangerReportReviewComponent.tsx";
import useGetPassangers from "@/hooks/passanger/useGetPassangers.tsx";
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
    //const { search } = useLocation();
    //const page = new URLSearchParams(search).get("page");
    //const currentPage = page == null ? INITIALPAGE : parseInt(page, 10);

    //const {isLoading, passangers} = useGetPassangers(isDriver , isPassanger, params, currentPassanger, trip);

    const {isLoading, passangers} = useGetPassangers(isDriver , !isDriver, params, passanger, trip);

    return (
        (!isLoading && passangers != undefined &&
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
                    {passangers.length != 0 &&
                        <div className={styles.passangerContainer}>
                            <div className={styles.titleContainer}>
                                <i className="bi bi-people-fill h3"></i>
                                <h3>{t('modal.passangers')}</h3>
                            </div>
                            {passangers.map((item, index) => (
                                <PassangerReportReviewComponent key={index} passanger={item} selectPassanger={selectUser}/>
                            ))}
                        </div>}
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
        </div>)
    );
};

export default ModalReportReview;
