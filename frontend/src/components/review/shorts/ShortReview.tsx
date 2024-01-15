import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";


const ShortReview = (review: ReviewModel) => {
  return (
    <div className={styles.short_review}>
      <div className={styles.row_content}>
        <div>
          <h4 className={styles.type}>{review.option}</h4>
          <span className={styles.review_info_comment}>{review.comment}</span>
        </div>
        <StarRating rating={review.rating} />
      </div>
      <div className={styles.date_review}>
        <span>{review.reviewDateTime}</span>
      </div>
    </div>
  );
};

export default ShortReview;
