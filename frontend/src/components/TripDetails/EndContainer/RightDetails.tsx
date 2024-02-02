import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Button, Modal } from "react-bootstrap";
import Status from "@/enums/Status.ts";
import { useState } from "react";
import userPublicModel from "@/models/UserPublicModel.ts";
import carModel from "@/models/CarModel.ts";
import { Link, useNavigate } from "react-router-dom";
import { createdTripsPath, reservedTripsPath } from "@/AppRouter.tsx";
import tripModel from "@/models/TripModel.ts";
import passangerModel from "@/models/PassangerModel.ts";
import passangerStatus from "@/enums/PassangerStatus.ts";
import RRModal from "@/components/TripDetails/ModalsRR/RRModal";
import ModalMake from "@/components/TripDetails/ModalsForms/ModalMake.tsx";
import ModalMakeCar from "@/components/TripDetails/ModalsForms/ModalMakeCar.tsx";
import useJoinTrip from "@/hooks/trips/useJoinTrip.tsx";
import useDeleteTrip from "@/hooks/trips/useDeleteTrip.tsx";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import useCancelTrip from "@/hooks/trips/useCancelTrip.tsx";
import getStatusPassanger from "@/functions/getStatusPassanger.tsx";
import IMake from "../IMake";
import ReserveStatus from "@/enums/ReserveStatus";

interface RightDetailsProps {
  isPassanger: boolean;
  isDriver: boolean;
  trip: tripModel;
  passanger: passangerModel | undefined;
  status: string;
  driver: userPublicModel;
  car: carModel;
  startDateTime: string;
  endDateTime: string;
}

const BtnJoin = ({
  trip,
  startDateTime,
  endDateTime,
}: {
  trip: tripModel;
  startDateTime: string;
  endDateTime: string;
}) => {
  const { t } = useTranslation();
  const { onSubmit: onSubmitAccept, invalidateTripState } = useJoinTrip(
    trip,
    startDateTime,
    endDateTime
  );

  return (
    <Button
      className={styles.btn_join}
      onClick={() => {
        onSubmitAccept();
        invalidateTripState();
      }}
    >
      <div className={styles.create_trip_btn}>
        <i className="bi bi-check-lg light-text"></i>
        <span>{t("trip_detail.btn.join")}</span>
      </div>
    </Button>
  );
};

const BtnDelete = ({ uri, id }: { uri: string; id: number }) => {
  const { t } = useTranslation();
  const { onSubmit: onSubmitDelete } = useDeleteTrip(uri, id);
  const navigate = useNavigate();

  const [showModalDelete, setModalDelete] = useState(false);
  const closeModalDelete = () => {
    setModalDelete(false);
  };
  const openModalDelete = () => {
    setModalDelete(true);
  };

  return (
    <div>
      <Button className={styles.btn_cancel} onClick={() => openModalDelete()}>
        <div className={styles.create_trip_btn}>
          <i className="bi bi-x light-text"></i>
          <span>{t("trip_detail.btn.delete")}</span>
        </div>
      </Button>

      <Modal
        show={showModalDelete}
        onHide={closeModalDelete}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <div className={styles.propProfile}>
          <Modal.Header closeButton>
            <Modal.Title>
              <h2 className={styles.titleModal}>{t("modal.delete_trip")}</h2>
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className={styles.review_empty_container}>
              <i className={`bi-solid bi-exclamation-triangle-fill h2`}></i>
              <h3 className="italic-text placeholder-text">
                {t("trip_detail.btn.delete_warning")}
              </h3>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <Button className={styles.backBtn} onClick={closeModalDelete}>
              {t("modal.Back")}
            </Button>
            <Button
              className={styles.cancelBtn}
              onClick={() => {
                onSubmitDelete();
                navigate(createdTripsPath);
              }}
            >
              {t("trip_detail.btn.delete")}
            </Button>
          </Modal.Footer>
        </div>
      </Modal>
    </div>
  );
};

const CancelBtn = ({ passanger }: { passanger?: passangerModel }) => {
  const { t } = useTranslation();
  const { onSubmit: onSubmitCancel } = useCancelTrip(
    passanger?.selfUri as string
  );
  const navigate = useNavigate();

  const [showModalCancel, setModalCancel] = useState(false);
  const closeModalCancel = () => {
    setModalCancel(false);
  };
  const openModalCancel = () => {
    setModalCancel(true);
  };

  return (
    <div>
      <Button className={styles.btn_cancel} onClick={() => openModalCancel()}>
        <div className={styles.create_trip_btn}>
          <i className="bi bi-x light-text"></i>
          <span>{t("trip_detail.btn.cancel")}</span>
        </div>
      </Button>

      <Modal
        show={showModalCancel}
        onHide={closeModalCancel}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <div className={styles.propProfile}>
          <Modal.Header closeButton>
            <Modal.Title>
              <h2 className={styles.titleModal}>{t("modal.cancel_trip")}</h2>
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className={styles.review_empty_container}>
              <i className={`bi-solid bi-exclamation-triangle-fill h2`}></i>
              <h3 className="italic-text placeholder-text">
                {t("trip_detail.btn.cancel_warning")}
              </h3>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <Button className={styles.backBtn} onClick={closeModalCancel}>
              {t("modal.back")}
            </Button>
            <Button
              className={styles.cancelBtn}
              onClick={() => {
                onSubmitCancel();
                navigate(reservedTripsPath);
              }}
            >
              {t("modal.cancel")}
            </Button>
          </Modal.Footer>
        </div>
      </Modal>
    </div>
  );
};

const RightDetails = ({
  isPassanger,
  isDriver,
  trip,
  passanger,
  status,
  driver,
  car,
  startDateTime,
  endDateTime,
}: RightDetailsProps) => {
  const { t } = useTranslation();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();
  const status_value =
    passanger != undefined ? getStatusPassanger(passanger) : status;
  // Modals Review
  const [showModalReport, setModalReport] = useState(false);
  const closeModalReport = () => {
    setModalReport(false);
  };
  const openModalReport = () => {
    setModalReport(true);
  };

  // Modals Report
  const [showModalReview, setModalReview] = useState(false);
  const closeModalReview = () => {
    setModalReview(false);
  };
  const openModalReview = () => {
    setModalReview(true);
  };

  // Modals car
  const [showModalCar, setModalCar] = useState(false);
  const closeModalCar = () => {
    setModalCar(false);
    openModalReview();
  };
  const openModalCar = () => {
    closeModalReview();
    setModalCar(true);
  };

  // Modals User
  const [make, setMake] = useState<IMake | null>(null);
  const [showModalMake, setModalMake] = useState(false);
  const closeModalMake = () => {
    setModalMake(false);
    if (make) {
      make.isReport ? openModalReport() : openModalReview();
      setMake(null);
    }
  };
  const openModalMake = (make: IMake) => {
    setMake(make);
    make.isReport ? closeModalReport() : closeModalReview();
    setModalMake(true);
  };

  return (
    !isLoadingDiscovery &&
    discovery &&
    (!isPassanger && !isDriver ? (
      status_value !== Status.FINISHED && (
        <div className={styles.btn_container}>
          <BtnJoin
            trip={trip}
            startDateTime={startDateTime}
            endDateTime={endDateTime}
          />
        </div>
      )
    ) : status_value === Status.FINISHED ? (
      isDriver || passanger?.passengerState == passangerStatus.ACCEPTED ? (
        <div className={styles.review_btn}>
          <div className={styles.btn_container}>
            <Button className={styles.btn_join} onClick={openModalReview}>
              <div className={styles.create_trip_btn}>
                <i className="bi bi-pencil-square"></i>
                <span>{t("trip_detail.btn.reviews")}</span>
              </div>
            </Button>
            <Link to={isDriver ? createdTripsPath : reservedTripsPath}>
              <Button className={styles.btn_trips}>
                <div className={styles.create_trip_btn}>
                  <i className="bi bi-car-front-fill light-text"></i>
                  <span>{t("trip_detail.btn.my_trips")}</span>
                </div>
              </Button>
            </Link>
          </div>
          <div className={styles.report_link}>
            <span>{t("trip_detail.report.pre_link_text")}</span>
            <span
              onClick={openModalReport}
              style={{ cursor: "pointer", color: "blue" }}
            >
              <i className="bi bi-car-front-fill"></i>
              {t("trip_detail.report.link_text")}
            </span>
          </div>

          <Modal
            show={showModalReport}
            onHide={closeModalReport}
            aria-labelledby="contained-modal-title-vcenter"
            centered
          >
            <RRModal
              title={t("modal.report.title")}
              openModalMake={openModalMake}
              closeModal={closeModalReport}
              driver={driver}
              isDriver={isDriver}
              trip={trip}
              passanger={passanger}
              reporting={true}
            />
          </Modal>

          <Modal
            show={showModalReview}
            onHide={closeModalReview}
            aria-labelledby="contained-modal-title-vcenter"
            centered
          >
            <RRModal
              title={t("modal.review.title")}
              titleClassName="secondary-text"
              openModalMake={openModalMake}
              closeModal={closeModalReview}
              driver={driver}
              isDriver={isDriver}
              trip={trip}
              passanger={passanger}
              car={car}
              reporting={false}
              openModalCar={openModalCar}
            />
          </Modal>

          <Modal
            show={showModalMake}
            onHide={closeModalMake}
            aria-labelledby="contained-modal-title-vcenter"
            centered
          >
            <ModalMake
              closeModal={closeModalMake}
              make={make}
              trip={trip}
              isCurrentUserDriver={isDriver}
            />
          </Modal>

          <Modal
            show={showModalCar}
            onHide={closeModalCar}
            aria-labelledby="contained-modal-title-vcenter"
            centered
          >
            <ModalMakeCar closeModal={closeModalCar} car={car} trip={trip} />
          </Modal>
        </div>
      ) : (
        <div className={styles.btn_container}>
          <Link to={reservedTripsPath}>
            <Button className={styles.btn_trips}>
              <div className={styles.create_trip_btn}>
                <i className="bi bi-car-front-fill light-text"></i>
                <span>{t("trip_detail.btn.my_trips")}</span>
              </div>
            </Button>
          </Link>
        </div>
      )
    ) : isDriver ? (
      <div className={styles.btn_container}>
        <Link to={createdTripsPath}>
          <Button className={styles.btn_trips}>
            <div className={styles.create_trip_btn}>
              <i className="bi bi-car-front-fill light-text"></i>
              <span>{t("trip_detail.btn.my_trips")}</span>
            </div>
          </Button>
        </Link>
        <BtnDelete
          uri={discovery?.tripsUriTemplate as string}
          id={trip.tripId}
        />
      </div>
    ) : (
      passanger &&
      passanger?.passengerState !== passangerStatus.REJECTED &&
      getStatusPassanger(passanger) === ReserveStatus.NOT_STARTED && (
        <div className={styles.btn_container}>
          <Link to={reservedTripsPath}>
            <Button className={styles.btn_trips}>
              <div className={styles.create_trip_btn}>
                <i className="bi bi-car-front-fill light-text"></i>
                <span>{t("trip_detail.btn.my_trips")}</span>
              </div>
            </Button>
          </Link>
          <CancelBtn passanger={passanger} />
        </div>
      )
    ))
  );
};

export default RightDetails;
