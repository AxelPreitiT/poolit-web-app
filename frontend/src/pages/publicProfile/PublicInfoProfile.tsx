import styles from "./styles.module.scss";
import ProfileProp from "@/components/profile/prop/ProfileProp.tsx";
import ProfileStars from "@/components/profile/stars/ProfileStars.tsx";
import ViewableProfileImg from "@/components/profile/img/VieweableProfileImg";
import UserPublicModel from "@/models/UserPublicModel";
import { useTranslation } from "react-i18next";

const PublicProfileInfo = ({ user }: { user: UserPublicModel }) => {
  const { t } = useTranslation();

  return (
    <div className={styles.profileCard}>
      <ViewableProfileImg src={user.imageUri} />
      <h3 className="text-center">
        {t("format.name", {
          name: user.username,
          surname: user.surname,
        })}
      </h3>
      <ProfileProp
        prop={t("profile.props.trips")}
        text={user.tripCount.toString()}
      />
      <ProfileStars
        prop={t("profile.props.rating_driver")}
        rating={user.driverRating}
      />
      <ProfileStars
        prop={t("profile.props.rating_passenger")}
        rating={user.passengerRating}
      />
    </div>
  );
};

export default PublicProfileInfo;
