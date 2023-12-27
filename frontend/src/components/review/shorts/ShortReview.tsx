import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";

interface ShortReviewProps {
  type: string;
  comment: string;
  raiting: number;
  formattedDate: string;
}

const ShortReview = ({
  type,
  comment,
  raiting,
  formattedDate,
}: ShortReviewProps) => {
  return (
    <div className={styles.short_review}>
      <div className={styles.row_content}>
        <div>
          <h4 className={styles.type}>{type}</h4>
          <span className={styles.review_info_comment}>{comment}</span>
        </div>
        <StarRating rating={raiting} />
      </div>
      <div className={styles.date_review}>
        <span>{formattedDate}</span>
      </div>
    </div>
  );
};

export default ShortReview;
