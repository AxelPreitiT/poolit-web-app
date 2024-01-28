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
import useGetCityById from "@/hooks/cities/useGetCityById.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import LoadingScreen from "@/components/loading/LoadingScreen";
import LoadingWheel from "@/components/loading/LoadingWheel";
import UsersRoles from "@/enums/UsersRoles";
import {INITIALPAGE, PROFILEPAGESIZE} from "@/enums/PaginationConstants.ts";

const ProfilePage = () => {
  const { t } = useTranslation();

  const { isLoading: isCurrentUserLoading, currentUser } = useCurrentUser();
  const { isLoading: isLoadingCity, city } = useGetCityById(
    currentUser?.cityUri
  );

  if (isCurrentUserLoading || isLoadingCity) {
    return <LoadingScreen description={t("profile.loading.profile")} />;
  }

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
      {isCurrentUserLoading || currentUser === undefined ? (
        <div className={styles.profileCard}>
          <LoadingWheel
            containerClassName={styles.loadingContainer}
            iconClassName={styles.loadingIcon}
            descriptionClassName={styles.loadingDescription}
            description={t("profile.loading.profile")}
          />
        </div>
      ) : (
        <ProfileInfo currentUser={currentUser} />
      )}

      <div className={styles.list_block}>
        {isCurrentUserLoading || currentUser === undefined ? (
          <LoadingScreen description={t("profile.loading.profile")} />
        ) : currentUser.role !== UsersRoles.USER ? (
          <TabComponent
            left_title={t("roles.passenger")}
            left_component={
              <PassengerList
                futureReservedTripsUri={createPaginationUri(
                  currentUser.futureReservedTripsUri,
                    INITIALPAGE,
                    PROFILEPAGESIZE
                )}
                pastReservedTripsUri={createPaginationUri(
                  currentUser.pastReservedTripsUri,
                    INITIALPAGE,
                    PROFILEPAGESIZE
                )}
                reviewsPassengerUri={createPaginationUri(
                  currentUser.reviewsPassengerUri,
                    INITIALPAGE,
                    PROFILEPAGESIZE
                )}
                id={currentUser.userId}
              />
            }
            right_title={t("roles.driver")}
            right_component={
              <DriverList
                futureCreatedTripsUri={createPaginationUri(
                  currentUser.futureCreatedTripsUri,
                    INITIALPAGE,
                    PROFILEPAGESIZE
                )}
                pastCreatedTripsUri={createPaginationUri(
                  currentUser.pastCreatedTripsUri,
                    INITIALPAGE,
                    PROFILEPAGESIZE
                )}
                reviewsDriverUri={createPaginationUri(
                  currentUser.reviewsDriverUri,
                    INITIALPAGE,
                    PROFILEPAGESIZE
                )}
                id={currentUser.userId}
              />
            }
            active="right"
          />
        ) : (
          <PassengerList
            futureReservedTripsUri={createPaginationUri(
              currentUser.futureReservedTripsUri,
                INITIALPAGE,
                PROFILEPAGESIZE
            )}
            pastReservedTripsUri={createPaginationUri(
              currentUser.pastReservedTripsUri,
                INITIALPAGE,
                PROFILEPAGESIZE
            )}
            reviewsPassengerUri={createPaginationUri(
              currentUser.reviewsPassengerUri,
                INITIALPAGE,
                PROFILEPAGESIZE
            )}
            id={currentUser.userId}
          />
        )}
      </div>
    </div>
  );
};

export default ProfilePage;
