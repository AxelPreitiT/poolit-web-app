import styles from "../ModalReportsReviews/styles.module.scss";
import { Form, Modal } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import TripModel from "@/models/TripModel";
import CarModel from "@/models/CarModel";
import useReviewsCar from "@/hooks/reportReview/useReviewsCar";
import useCarReviewForm from "@/hooks/forms/useCarReviewForm";
import LoadingWheel from "@/components/loading/LoadingWheel";
import ModalMakeHeader from "./ModalMakeHeader";
import ModalMakeFooter from "./ModalMakeFooter";
import ReviewForm from "./ReviewForm";
import ImageService from "@/services/ImageService.ts";

export interface ModalCarMakeReviewProps {
  closeModal: () => void;
  car: CarModel;
  trip: TripModel;
}

const ModalMakeCar = ({ closeModal, car, trip }: ModalCarMakeReviewProps) => {
  const { t } = useTranslation();
  const { invalidateCarReviewQuery } = useReviewsCar(trip);

  const onSuccess = () => {
    invalidateCarReviewQuery();
    closeModal();
  };

  const carReviewForm = useCarReviewForm({
    car,
    trip,
    onSuccess,
  });
  const {
    handleSubmit,
    isFetching,
    isReviewOptionsLoading: isCarReviewOptionsLoading,
    isReviewOptionsError: isCarReviewOptionsError,
    reviewOptions: carReviewOptions,
  } = carReviewForm;

  if (
    isCarReviewOptionsLoading ||
    !carReviewOptions ||
    isCarReviewOptionsError
  ) {
    return <LoadingWheel description={t("car_review.loading")} />;
  }

  return (
    <Form className={styles.propProfile} onSubmit={handleSubmit}>
      <ModalMakeHeader title={car.infoCar} imageSrc={ImageService.getSmallImageUrl(car.imageUri)} />
      <Modal.Body>
        <div className={styles.categoryContainer}>
          <ReviewForm
            createReviewForm={carReviewForm}
            ratingTitle={t("reviews.stars_car_title")}
            optionTitle={t("reviews.option_car_title")}
          />
        </div>
      </Modal.Body>
      <ModalMakeFooter onClose={closeModal} isFetching={isFetching} />
    </Form>
  );
};

export default ModalMakeCar;
