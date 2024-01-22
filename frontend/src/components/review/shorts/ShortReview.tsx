import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";
import {useTranslation} from "react-i18next";
import getFormattedDateTime from "@/functions/DateFormat.ts";


const ShortReview = (review: ReviewModel) => {
    const { t } = useTranslation();

    return (
    <div className={styles.short_review}>
      <div className={styles.row_content}>
        <div>
          <h4 className={styles.type}>{t(`reviews.${review.option}`)}</h4>
          <span className={styles.review_info_comment}>{review.comment}</span>
        </div>
        <StarRating rating={review.rating} />
      </div>
      <div className={styles.date_review}>
        <span>{getFormattedDateTime(review.reviewDateTime).date}</span>
      </div>
    </div>
  );
};

export default ShortReview;
