import CityModel from "@/models/CityModel";
import UserPrivateModel from "@/models/UserPrivateModel";
import styles from "./styles.module.scss";
import ViewableProfileImg from "@/components/profile/img/VieweableProfileImg";
import { useTranslation } from "react-i18next";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import { Button } from "react-bootstrap";
import { BsPencilSquare } from "react-icons/bs";
import { useState } from "react";
import { useCurrentUser } from "@/hooks/users/useCurrentUser";
import EditProfileForm from "./EditProfileForm";

interface ProfileInfoProps {
  currentUser: UserPrivateModel;
  cities: CityModel[];
  city: CityModel;
}

const ProfileInfo = ({ currentUser, cities, city }: ProfileInfoProps) => {
  const { t } = useTranslation();
  const { invalidate: invalidateCurrentUser } = useCurrentUser();
  const [editMode, setEditMode] = useState(false);

  return (
    <div className={styles.profileCard}>
      {editMode ? (
        <EditProfileForm
          user={currentUser}
          city={city}
          cities={cities}
          onSuccess={() => {
            invalidateCurrentUser();
            setEditMode(false);
          }}
          onCancel={() => setEditMode(false)}
        />
      ) : (
        <>
          <ViewableProfileImg src={currentUser.imageUri} />
          <h3 className="text-center">
            {t("format.name", {
              name: currentUser.username,
              surname: currentUser.surname,
            })}
          </h3>
          <ProfileProp
            prop={t("profile.props.email")}
            text={currentUser.email}
          />
          <ProfileProp
            prop={t("profile.props.phone")}
            text={currentUser.phone}
          />
          <ProfileProp
            prop={t("profile.props.neighborhood")}
            text={city.name}
          />
          <ProfileProp
            prop={t("profile.props.language")}
            text={t(`profile.props.${currentUser.mailLocale}`)}
          />
          <ProfileProp
            prop={t("profile.props.trips")}
            text={currentUser.tripCount.toString()}
          />
          <ProfileStars
            prop={t("profile.props.rating_driver")}
            rating={currentUser.driverRating}
          />
          <ProfileStars
            prop={t("profile.props.rating_passenger")}
            rating={currentUser.passengerRating}
          />
          <div className={styles.editButtonContainer}>
            <Button className="secondary-btn" onClick={() => setEditMode(true)}>
              <BsPencilSquare className="light-text h4" />
              <span className="light-text h4">{t("profile.edit")}</span>
            </Button>
          </div>
        </>
      )}
    </div>
  );
};

export default ProfileInfo;
