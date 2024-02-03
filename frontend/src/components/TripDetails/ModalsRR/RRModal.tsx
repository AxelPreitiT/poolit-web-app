import styles from "./styles.module.scss";
import { Button, Modal } from "react-bootstrap";
import DriverReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/DriverReportReviewComponent.tsx";
import userPublicModel from "@/models/UserPublicModel.ts";
import { useTranslation } from "react-i18next";
import carModel from "@/models/CarModel.ts";
import CarReviewComponent from "@/components/TripDetails/ModalReportsReviews/CarReviewComponent";
import passangerModel from "@/models/PassangerModel.ts";
import tripModel from "@/models/TripModel.ts";
import PassangerReportReviewComponent from "@/components/TripDetails/ModalReportsReviews/PassangerReportReviewComponent.tsx";
import getUriPassangers from "@/functions/getUriPassangers.tsx";
import PaginationComponentExtraData from "@/components/pagination/PaginationComponent/PaginationComponentExtraData.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import { INITIALPAGE, PASSANGERPAGESIZE } from "@/enums/PaginationConstants.ts";
import usePassangerByUri from "@/hooks/passanger/usePassangerByUri.tsx";
import IMake from "../IMake";

export interface ModalReportProps {
  title: string;
  titleClassName?: string;
  openModalMake: (make: IMake) => void;
  openModalCar?: () => void;
  closeModal: () => void;
  driver: userPublicModel;
  isDriver: boolean;
  trip: tripModel;
  passanger: passangerModel | undefined;
  car?: carModel;
  reporting: boolean;
}

const RRModal = ({
  title,
  titleClassName,
  closeModal,
  driver,
  car,
  isDriver,
  trip,
  passanger,
  reporting,
  openModalMake,
  openModalCar,
}: ModalReportProps) => {
  const { t } = useTranslation();
  const uri = getUriPassangers(isDriver, trip, passanger);

  return (
    <div className={styles.propProfile}>
      <Modal.Header closeButton>
        <Modal.Title>
          <h2 className={styles.titleModal + " " + titleClassName}>{title}</h2>
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className={styles.categoryContainer}>
          {!isDriver && (
            <div className={styles.passangerContainer}>
              <div className={styles.titleContainer}>
                <i className="bi bi-person-fill h3"></i>
                <h3>{t("modal.driver")}</h3>
              </div>
              <DriverReportReviewComponent
                driver={driver}
                trip={trip}
                reporting={reporting}
                openModalMake={openModalMake}
              />
            </div>
          )}
          {!isDriver && car && openModalCar && (
            <div className={styles.passangerContainer}>
              <div className={styles.titleContainer}>
                <i className="bi bi-car-front-fill h3"></i>
                <h3>{t("modal.car")}</h3>
              </div>
              <CarReviewComponent
                car={car}
                trip={trip}
                openModalCar={openModalCar}
              />
            </div>
          )}
          <div className={styles.passangerContainer}>
            <div className={styles.titleContainer}>
              <i className="bi bi-people-fill h3"></i>
              <h3>{t("modal.passangers")}</h3>
            </div>
            <PaginationComponentExtraData
              CardComponent={PassangerReportReviewComponent}
              extraData={{ reporting, openModalMake }}
              uri={createPaginationUri(uri, INITIALPAGE, PASSANGERPAGESIZE)}
              useFuction={usePassangerByUri}
              empty_component={
                <div className={styles.review_empty_container}>
                  <h3 className="italic-text placeholder-text">
                    {t("trip_detail.passengers.empty_modal")}
                  </h3>
                </div>
              }
              itemsName={t("trip_detail.passengers.header")}
            />
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button className="primary-btn" onClick={closeModal}>
          {t("modal.close")}
        </Button>
      </Modal.Footer>
    </div>
  );
};

export default RRModal;
