import ProfileImg from "@/components/profile/img/ProfileImg";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import DriverList from "@/components/profile/ProfileLists/DriverList";
import PassengerList from "@/components/profile/ProfileLists/PassangerList";
import TabComponent from "@/components/tab/TabComponent";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import UserPrivateModel from "@/models/UserPrivateModel.ts";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import useGetCityById from "@/hooks/cities/useGetCityById.tsx";

const ProfilePage = () => {
  const { t } = useTranslation();

  const { isLoading, currentUser } = useCurrentUser();
  const {isLoading: isLoadingCity, city} = useGetCityById(currentUser?.cityUri);

  const ProfileInfo = ({ currentUser }: { currentUser: UserPrivateModel }) => (
    <div className={styles.profileCard}>
      <ProfileImg src={currentUser.imageUri} />
      <h3 className="text-center">
        {t("format.name", {
          name: currentUser.username,
          surname: currentUser.surname,
        })}
      </h3>
      <ProfileProp prop={t("profile.props.email")} text={currentUser.email} />
      <ProfileProp prop={t("profile.props.phone")} text={currentUser.phone} />
      {city === undefined || isLoadingCity ? (
        <ProfileProp
          prop={t("profile.props.neighborhood")}
          text={t("spinner.loading")}
        />
      ) : (
        <ProfileProp prop={t("profile.props.neighborhood")} text={city.name} />
      )}
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
    </div>
  );

  return (
    <div className={styles.main_container}>
      {isLoading || currentUser === undefined ? (
        <div className={styles.profileCard}>
          <SpinnerComponent />
        </div>
      ) : (
        <ProfileInfo currentUser={currentUser} />
      )}

      <div className={styles.list_block}>
        {isLoading || currentUser === undefined ? (
          <TabComponent
            right_title={t("roles.passenger")}
            right_component={<SpinnerComponent />}
            left_title={t("roles.driver")}
            left_component={<SpinnerComponent />}
          />
        ) : (
          <TabComponent
            right_title={t("roles.passenger")}
            right_component={
              <PassengerList
                futureReservedTripsUri={currentUser.futureReservedTripsUri}
                pastReservedTripsUri={currentUser.pastReservedTripsUri}
                selfUri={currentUser.selfUri}
              />
            }
            left_title={t("roles.driver")}
            left_component={
              <DriverList
                futureCreatedTripsUri={currentUser.futureCreatedTripsUri}
                pastCreatedTripsUri={currentUser.pastCreatedTripsUri}
                currentUser={currentUser}
              />
            }
          />
        )}
      </div>
    </div>
  );
};

export default ProfilePage;
