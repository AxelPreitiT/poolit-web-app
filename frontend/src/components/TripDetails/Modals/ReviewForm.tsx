import { useState } from "react";
import styles from "./styles.module.scss";
import { Form, FormSelect } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import PassengerReviewsOptions from "@/enums/PassengerReviewsOptions";

const ReviewForm = () => {
  const { t } = useTranslation();
  const [selectedOption, setSelectedOption] = useState<PassengerReviewsOptions>(
    PassengerReviewsOptions.ACCEPTABLE_ATTITUDE
  );
  const [selectedStarsOption, setSelectedStarsOption] = useState(1);

  const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedOption(e.target.value as PassengerReviewsOptions);
  };

  const handleStarsOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedStarsOption(parseInt(e.target.value as string, 10));
  };

  return (
    <div className={styles.picker_style}>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reviews.stars_title")}</strong>
        </label>
        <FormSelect
          id="passenger-38"
          name="rating"
          className={styles.stars_option}
          value={selectedStarsOption}
          onChange={handleStarsOptionChange}
        >
          <option value="1" className={styles.stars_option}>
            ★
          </option>
          <option value="2" className={styles.stars_option}>
            ★★
          </option>
          <option value="3" className={styles.stars_option}>
            ★★★
          </option>
          <option value="4" className={styles.stars_option}>
            ★★★★
          </option>
          <option value="5" className={styles.stars_option}>
            ★★★★★
          </option>
        </FormSelect>
      </div>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reviews.option_title")}</strong>
        </label>
        <FormSelect
          id="reviewPicker"
          value={selectedOption}
          onChange={handleOptionChange}
        >
          {Object.values(PassengerReviewsOptions).map((option) => (
            <option key={option} value={option}>
              {t(`reviews.${option}`)}
            </option>
          ))}
        </FormSelect>
      </div>
      <div>
        <label className="report-option-label">
          <strong className="text">{t("reviews.text_title")}</strong>
        </label>
        <Form.Control as="textarea" rows={3} />
      </div>
    </div>
  );
};

export default ReviewForm;
