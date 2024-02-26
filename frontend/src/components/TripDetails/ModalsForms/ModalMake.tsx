import styles from "../ModalReportsReviews/styles.module.scss";
import { useTranslation } from "react-i18next";
import IMake from "../IMake";
import LoadingWheel from "@/components/loading/LoadingWheel";
import ModalMakeHeader from "./ModalMakeHeader";
import ReportForm from "./ReportForm";
import DriverReviewForm from "./DriverReviewForm";
import PassengerReviewForm from "./PassengerReviewForm";
import TripModel from "@/models/TripModel";
import UserPublicModel from "@/models/UserPublicModel";
import { Modal } from "react-bootstrap";
import { useState } from "react";
import ModalMakeFooter from "./ModalMakeFooter";
import ImageService from "@/services/ImageService.ts";

interface ModalMakeProps {
  closeModal: () => void;
  make: IMake | null;
  isCurrentUserDriver: boolean;
  trip: TripModel;
}

export interface ModalMakeFormProps {
  onClose: () => void;
  isCurrentUserDriver: boolean;
  isUserDriver: boolean;
  user: UserPublicModel;
  trip: TripModel;
  formId: string;
  setIsFetching: (isFetching: boolean) => void;
}

const ModalMake = ({
  closeModal,
  make,
  trip,
  isCurrentUserDriver,
}: ModalMakeProps) => {
  const { t } = useTranslation();
  const [isFetching, setIsFetching] = useState(false);

  if (!make) {
    return <LoadingWheel description={t("car_review.loading")} />;
  }

  const { user, isReport, isDriver } = make;
  const Form: React.FC<ModalMakeFormProps> = isReport
    ? ReportForm
    : isDriver
    ? DriverReviewForm
    : PassengerReviewForm;
  const formId = `form-${isReport ? "report" : "review"}-${user.userId}`;

  return (
    <div className={styles.propProfile}>
      <ModalMakeHeader
        title={t("format.name", {
          name: user.username,
          surname: user.surname,
        })}
        imageSrc={ImageService.getSmallImageUrl(user.imageUri)}
      />
      <Modal.Body>
        <div className={styles.categoryContainer}>
          <Form
            onClose={closeModal}
            isCurrentUserDriver={isCurrentUserDriver}
            isUserDriver={isDriver}
            user={user}
            trip={trip}
            formId={formId}
            setIsFetching={setIsFetching}
          />
        </div>
      </Modal.Body>
      <ModalMakeFooter
        onClose={closeModal}
        isFetching={isFetching}
        formId={formId}
      />
    </div>
  );
};

export default ModalMake;
