import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import {useTranslation} from "react-i18next";

interface StarRatingProps {
  rating: number;
  color?: string;
  size?: string;
}

const StarRating = ({
  rating,
  color = "#ffa216",
  size = "medium",
}: StarRatingProps) => {
  const { t } = useTranslation();

  const renderStar = (index: number) => {
    const remainder = rating - index + 0.5;
    const starStyle = {
      color,
      fontSize: size,
    };

    if (remainder >= 1) {
      return <i className="bi bi-star-fill" style={starStyle} key={index}></i>;
    } else if (remainder > 0) {
      return <i className="bi bi-star-half" style={starStyle} key={index}></i>;
    } else {
      return <i className="bi bi-star" style={starStyle} key={index}></i>;
    }
  };

  const textStyle = {
    fontSize: size,
  };


  return (
      <div className={styles.stars}>
        {rating > 0 ?
            [1, 2, 3, 4, 5].map((index) => renderStar(index) as React.ReactElement) :
            <span style={textStyle}>{t('reviews.no_raiting')}</span>
        }
      </div>
  );
};

export default StarRating;
