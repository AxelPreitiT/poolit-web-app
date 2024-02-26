import { useTranslation } from "react-i18next";
import { ModalMakeFormProps } from "./ModalMake";
import useReviewsReportPassangers from "@/hooks/reportReview/useReviewsPassangers";
import usePassengerReviewForm from "@/hooks/forms/usePassengerReviewForm";
import { useEffect } from "react";
import ReviewForm from "./ReviewForm";
import { Form } from "react-bootstrap";

const PassengerReviewForm = ({
  onClose,
  formId,
  setIsFetching,
  trip,
  user,
}: ModalMakeFormProps) => {
  const { t } = useTranslation();
  const { invalidatePassengerReviewQuery } = useReviewsReportPassangers();

  const onSuccess = () => {
    invalidatePassengerReviewQuery(user);
    onClose();
  };

  const createPassengerReviewForm = usePassengerReviewForm({
    passenger: user,
    trip,
    onSuccess,
  });

  const { handleSubmit, isFetching } = createPassengerReviewForm;

  useEffect(() => {
    setIsFetching(isFetching);
  }, [isFetching, setIsFetching]);

  return (
    <Form id={formId} onSubmit={handleSubmit}>
      <ReviewForm
        createReviewForm={createPassengerReviewForm}
        ratingTitle={t("reviews.stars_title")}
        optionTitle={t("reviews.option_title")}
      />
    </Form>
  );
};

export default PassengerReviewForm;
