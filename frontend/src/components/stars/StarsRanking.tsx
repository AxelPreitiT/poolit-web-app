import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import { BiSolidStar, BiSolidStarHalf, BiStar } from "react-icons/bi";

interface StarRatingProps {
  rating: number;
  className?: string;
}

const StarRating = ({ rating, className }: StarRatingProps) => {
  const renderStar = (index: number) => {
    const result = rating - (index - 1);
    const starClassName = styles.star + " " + className;

    if (result >= 1) {
      return <BiSolidStar className={starClassName} key={index}></BiSolidStar>;
    } else if (result >= 0.5) {
      return (
        <BiSolidStarHalf
          className={starClassName}
          key={index}
        ></BiSolidStarHalf>
      );
    } else {
      return <BiStar className={starClassName} key={index}></BiStar>;
    }
  };

  return (
    <div className={styles.stars}>
      {[1, 2, 3, 4, 5].map((index) => renderStar(index))}
    </div>
  );
};

export default StarRating;
