import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import usePublicUserById from "@/hooks/users/usePublicUserById.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";
import ListProfileContainer from "@/components/profile/list/ListProfileContainer.tsx";
import {
  publicsDriverReviewsPath,
  publicsPassangerReviewsPath,
} from "@/AppRouter.tsx";
import LoadingWheel from "@/components/loading/LoadingWheel";
import ShortReviewProfile from "@/components/review/shorts/ShortReviewProfile.tsx";
import PublicProfileInfo from "./PublicInfoProfile";

const PublicProfilePage = () => {
  const { t } = useTranslation();
  const params = useParams();
  const { isLoading: isLoadingUser, user: user } = usePublicUserById(params.id);
  const { isLoading: isLoadingReviewsDriver, data: reviewsDriver } =
    useUserReviewsByUri(user?.reviewsDriverUri);
  const { isLoading: isLoadingReviewsPassanger, data: reviewsPassanger } =
    useUserReviewsByUri(user?.reviewsPassengerUri);

  return (
    <div className={styles.main_container}>
      {isLoadingUser || user === undefined ? (
        <LoadingWheel
          containerClassName={styles.loadingContainer}
          iconClassName={styles.loadingIcon}
          descriptionClassName={styles.loadingDescription}
          description={t("profile.loading.profile")}
        />
      ) : (
        <PublicProfileInfo user={user} />
      )}

      <div className={styles.list_block}>
        {isLoadingReviewsDriver || reviewsDriver === undefined ? (
          <LoadingWheel
            containerClassName={styles.loadingContainer}
            iconClassName={styles.loadingSmallIcon}
            descriptionClassName={styles.loadingSmallDescription}
            description={t("profile.loading.reviews")}
          />
        ) : (
          <ListProfileContainer
            title={t("profile.lists.review_as_driver")}
            btn_footer_text={t("profile.lists.review_more")}
            empty_text={t("profile.lists.review_empty")}
            empty_icon={"book"}
            data={reviewsDriver.data}
            component_name={ShortReviewProfile}
            link={publicsDriverReviewsPath.replace(":id", String(params.id))}
          />
        )}
        {isLoadingReviewsPassanger || reviewsPassanger === undefined ? (
          <LoadingWheel
            containerClassName={styles.loadingContainer}
            iconClassName={styles.loadingSmallIcon}
            descriptionClassName={styles.loadingSmallDescription}
            description={t("profile.loading.reviews")}
          />
        ) : (
          <ListProfileContainer
            title={t("profile.lists.review_as_passanger")}
            btn_footer_text={t("profile.lists.review_more")}
            empty_text={t("profile.lists.review_empty")}
            empty_icon={"book"}
            data={reviewsPassanger.data}
            component_name={ShortReviewProfile}
            link={publicsPassangerReviewsPath.replace(":id", String(params.id))}
          />
        )}
      </div>
    </div>
  );
};

export default PublicProfilePage;
