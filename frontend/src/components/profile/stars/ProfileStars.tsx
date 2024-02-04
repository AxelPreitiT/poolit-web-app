import { useTranslation } from "react-i18next";
import styles from "./../prop/styles.module.scss";
import StarRating from "@/components/stars/StarsRanking";
import ProfilePropHeader from "../prop/ProfilePropHeader";

interface ProfileStarsProps {
  prop: string;
  rating: number;
}

const ProfileStars = ({ prop, rating }: ProfileStarsProps) => {
  const { t } = useTranslation();

  return (
    <div>
      <ProfilePropHeader prop={prop} />
      {rating && rating > 0 ? (
        <StarRating rating={rating} className={styles.rating} />
      ) : (
        <p className={styles.propValue}>{t("profile.props.no_rating")}</p>
      )}
    </div>
  );
};

export default ProfileStars;
