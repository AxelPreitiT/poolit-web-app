import styles from "./styles.module.scss";
import { Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import useReviewForm from "@/hooks/forms/useReviewForm";
import { Controller } from "react-hook-form";
import RatingSelector from "@/components/forms/RatingSelector/RatingSelector";
import FormError from "@/components/forms/FormError/FormError";

interface ReviewFormProps {
  createReviewForm: ReturnType<typeof useReviewForm>;
  ratingTitle: string;
  optionTitle: string;
}

const ReviewForm = ({
  createReviewForm,
  ratingTitle,
  optionTitle,
}: ReviewFormProps) => {
  const { t } = useTranslation();
  const {
    register,
    control,
    formState: { errors },
    tFormError,
    onRatingChange,
    reviewOptions = [],
  } = createReviewForm;

  return (
    <div className={styles.picker_style}>
      <div>
        <label className="report-option-label">
          <strong className="text">{ratingTitle}</strong>
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
          <strong className="text">{optionTitle}</strong>
        </label>
        <Form.Select {...register("option")}>
          {reviewOptions.map((option) => (
            <option key={option.id} value={option.id}>
              {t(`reviews.${option.id}`)}
            </option>
          ))}
        </Form.Select>
        <FormError error={tFormError(errors.option)} className={styles.error} />
      </div>
      <div>
        <div className={styles.optionalContainer}>
          <label className="report-option-label">
            <strong className="text">{t("reviews.text_title")}</strong>
          </label>
          <label className={styles.optionalLabel}>
            {t("reviews.optional")}
          </label>
        </div>
        <Form.Control as="textarea" rows={3} {...register("comment")} />
        <FormError
          error={tFormError(errors.comment)}
          className={styles.error}
        />
      </div>
    </div>
  );
};

export default ReviewForm;
