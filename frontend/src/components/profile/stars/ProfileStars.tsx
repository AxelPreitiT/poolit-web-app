import styles from "./../prop/styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";

interface ProfileStarsProps {
  prop: string;
  rating: number;
}

const ProfileStars = ({ prop, rating }: ProfileStarsProps) => {
  return (
    <div>
      <h6 className={styles.propProfile}>{prop}</h6>
      <StarRating rating={rating} />
    </div>
  );
};

export default ProfileStars;
