import styles from "./styles.module.scss";
import { Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import useCarReviewForm from "@/hooks/forms/useCarReviewForm";
import CarReviewOptionModel from "@/models/CarReviewOptionModel";
import { Controller } from "react-hook-form";
import RatingSelector from "@/components/forms/RatingSelector/RatingSelector";
import FormError from "@/components/forms/FormError/FormError";

interface ReviewCarFormProps {
  createCarReviewForm: ReturnType<typeof useCarReviewForm>;
  onRatingChange: (rating: number) => void;
  carReviewOptions: CarReviewOptionModel[];
}

const ReviewCarForm = ({
  createCarReviewForm,
  onRatingChange,
  carReviewOptions,
}: ReviewCarFormProps) => {
  const { t } = useTranslation();
  const {
    register,
    control,
    formState: { errors },
    tFormError,
  } = createCarReviewForm;

  return (
    <div className={styles.picker_style}>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reviews.stars_car_title")}</strong>
        </label>
        <Controller
          name="rating"
          control={control}
          render={({ field: { onChange, value } }) => (
            <RatingSelector
              rating={value}
              onChange={(event) => {
                const rating = parseInt(event.target.value, 10);
                onChange(rating);
                onRatingChange(rating);
              }}
              className={styles.stars_option}
            />
          )}
        />
        <FormError error={tFormError(errors.rating)} className={styles.error} />
      </div>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reviews.option_car_title")}</strong>
        </label>
        <Form.Select {...register("option")}>
          {carReviewOptions.map((option) => (
            <option key={option.id} value={option.id}>
              {t(`reviews.${option.id}`)}
            </option>
          ))}
        </Form.Select>
        <FormError error={tFormError(errors.option)} className={styles.error} />
      </div>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reviews.text_title")}</strong>
        </label>
        <Form.Control as="textarea" rows={3} {...register("comment")} />
        <FormError
          error={tFormError(errors.comment)}
          className={styles.error}
        />
      </div>
    </div>
  );
};

export default ReviewCarForm;
