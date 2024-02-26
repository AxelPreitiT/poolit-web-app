import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import DriverList from "@/components/profile/ProfileLists/DriverList";
import PassengerList from "@/components/profile/ProfileLists/PassangerList";
import TabComponent from "@/components/tab/TabComponent";
import { useCurrentUser } from "@/hooks/users/useCurrentUser.tsx";
import useCityByUri from "@/hooks/cities/useCityByUri";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import LoadingScreen from "@/components/loading/LoadingScreen";
import LoadingWheel from "@/components/loading/LoadingWheel";
import UsersRoles from "@/enums/UsersRoles";
import { INITIALPAGE, PROFILEPAGESIZE } from "@/enums/PaginationConstants.ts";
import { useState } from "react";
import useAllCities from "@/hooks/cities/useAllCities";
import ProfileInfo from "./ProfileInfo";

const ProfilePage = () => {
  const { t } = useTranslation();

  const { isLoading: isCurrentUserLoading, currentUser } = useCurrentUser();
  const { isLoading: isLoadingCity, city } = useCityByUri(currentUser?.cityUri);
  const { isLoading: isAllCitiesLoading, cities } = useAllCities();
  const [active, setActive] = useState<"left" | "right">("right");

  if (isCurrentUserLoading || isAllCitiesLoading || isLoadingCity) {
    return <LoadingScreen description={t("profile.loading.profile")} />;
  }

  return (
    <div className={styles.main_container}>
      {!currentUser || !city || !cities ? (
        <div className={styles.profileCard}>
          <LoadingWheel
            containerClassName={styles.loadingContainer}
            iconClassName={styles.loadingIcon}
            descriptionClassName={styles.loadingDescription}
            description={t("profile.loading.profile")}
          />
        </div>
      ) : (
        <ProfileInfo currentUser={currentUser} cities={cities} city={city} />
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
            active={active}
            onLeftClick={() => setActive("left")}
            onRightClick={() => setActive("right")}
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
