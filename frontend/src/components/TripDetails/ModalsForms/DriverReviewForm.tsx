import useDriverReviewForm from "@/hooks/forms/useDriverReviewForm";
import { ModalMakeFormProps } from "./ModalMake";
import useReviewsReportsDriver from "@/hooks/reportReview/useReviewsReportsDriver";
import { useEffect } from "react";
import { Form } from "react-router-dom";
import ReviewForm from "./ReviewForm";
import { useTranslation } from "react-i18next";

const DriverReviewForm = ({
  onClose,
  formId,
  setIsFetching,
  trip,
  user,
}: ModalMakeFormProps) => {
  const { t } = useTranslation();
  const { invalidateDriverReviewQuery } = useReviewsReportsDriver(false, trip);

  const onSuccess = () => {
    invalidateDriverReviewQuery();
    onClose();
  };

  const createDriverReviewForm = useDriverReviewForm({
    driver: user,
    trip,
    onSuccess,
  });

  const { handleSubmit, isFetching } = createDriverReviewForm;

  useEffect(() => {
    setIsFetching(isFetching);
  }, [isFetching, setIsFetching]);

  return (
    <Form id={formId} onSubmit={handleSubmit}>
      <ReviewForm
        createReviewForm={createDriverReviewForm}
        ratingTitle={t("reviews.stars_title")}
        optionTitle={t("reviews.option_title")}
      />
    </Form>
  );
};

export default DriverReviewForm;
