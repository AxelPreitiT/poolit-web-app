import "bootstrap/dist/css/bootstrap.min.css";
import { Container, Row } from "react-bootstrap";
import styles from "./styles.module.scss";

interface StarRatingProps {
  rating: number;
  text: string;
}

const StarRating = ({ rating, text }: StarRatingProps) => {
  const renderStar = (index: number) => {
    const remainder = rating - index + 0.5;
    if (remainder >= 1) {
      return <i className="bi bi-star-fill text-warning h4" key={index}></i>;
    } else if (remainder > 0) {
      return <i className="bi bi-star-half text-warning h4" key={index}></i>;
    } else {
      return <i className="bi bi-star text-warning h4" key={index}></i>;
    }
  };

  return (
    <Container>
      <Row>
        <h6 className={styles.propProfile}>{text}</h6>
        <div className={styles.stars}>
          {[1, 2, 3, 4, 5].map((index) => renderStar(index))}
        </div>
      </Row>
    </Container>
  );
};

export default StarRating;
