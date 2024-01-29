import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";
import { useTranslation } from "react-i18next";
import ReviewModel from "@/models/ReviewModel";
import { capitalize } from "@/utils/string/capitalize";

const formatDateTime = (dateTime: string) => {
    const [date, time] = dateTime.split("T");
    const [year, month, day] = date.split("-");
    const [hour, minutes] = time.split(":");

    return `${day}/${month}/${year} ${hour}:${minutes}`;
};

const ShortReviewProfile = (data: ReviewModel) => {
    const { t } = useTranslation();

    return (
        <div className={styles.short_review}>
            <div className={styles.row_content}>
                <div>
                    <h4 className={styles.type}>{t(`reviews.${data.option}`)}</h4>
                    <span className={styles.review_info_comment}>
            {capitalize(data.comment)}
          </span>
                </div>
                <StarRating rating={data.rating} className="h4" />
            </div>
            <div className={styles.date_review}>
                <span>{formatDateTime(data.reviewDateTime)}</span>
            </div>
        </div>
    );
};

export default ShortReviewProfile;
