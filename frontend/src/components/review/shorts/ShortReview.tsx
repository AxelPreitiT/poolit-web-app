import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";
import { useTranslation } from "react-i18next";
import { capitalize } from "@/utils/string/capitalize";
import reviewModel from "@/models/ReviewModel";

const formatDateTime = (dateTime: string) => {
  const [date, time] = dateTime.split("T");
  const [year, month, day] = date.split("-");
  const [hour, minutes] = time.split(":");

  return `${day}/${month}/${year} ${hour}:${minutes}`;
};

interface ShortReviewProps {
    data: reviewModel;
}

const ShortReview = ({data: review} : ShortReviewProps) => {
  const { t } = useTranslation();

  return (
    <div className={styles.short_review}>
      <div className={styles.row_content}>
        <div>
          <h4 className={styles.type}>{t(`reviews.${review.option}`)}</h4>
          <span className={styles.review_info_comment}>
            {capitalize(review.comment)}
          </span>
        </div>
        <StarRating rating={review.rating} className="h4" />
      </div>
      <div className={styles.date_review}>
        <span>{formatDateTime(review.reviewDateTime)}</span>
      </div>
    </div>
  );
};

export default ShortReview;
