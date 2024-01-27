import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { BiSolidStar, BiSolidStarHalf } from "react-icons/bi";

interface StarRatingProps {
  rating: number;
  className?: string;
}

const StarRating = ({ rating, className }: StarRatingProps) => {
  const renderStar = (index: number) => {
    const remainder = rating - index + 0.5;
    const starClassName = styles.star + " " + className;

    if (remainder >= 1) {
      return <BiSolidStar className={starClassName} key={index}></BiSolidStar>;
    } else if (remainder > 0) {
      return (
        <BiSolidStarHalf
          className={starClassName}
          key={index}
        ></BiSolidStarHalf>
      );
    } else {
      return <BiSolidStar className={starClassName} key={index}></BiSolidStar>;
    }
  };

  return (
    <div className={styles.stars}>
      {[1, 2, 3, 4, 5].map((index) => renderStar(index))}
    </div>
  );
};

export default StarRating;
