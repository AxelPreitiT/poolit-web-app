import ProfileImg from "@/components/profile/img/ProfileImg.tsx";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfileProp from "@/components/profile/prop/ProfileProp.tsx";
import ShortReview from "@/components/review/shorts/ShortReview.tsx";
import ProfileStars from "@/components/profile/stars/ProfileStars.tsx";
import {useParams} from "react-router-dom";
import usePublicUserById from "@/hooks/users/usePublicUserById.tsx";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";
import ListProfileContainer from "@/components/profile/list/ListProfileContainer.tsx";
import {publicsReviewsPath} from "@/AppRouter.tsx";

const PublicProfilePage = () => {
  const { t } = useTranslation();
  const params = useParams();
  const { isLoading: isLoadingUser, user: user } = usePublicUserById(params.id);
  const { isLoading: isLoadingReviewsDriver, reviews:reviewsDriver } = useUserReviewsByUri(user?.reviewsDriverUri);
  const { isLoading: isLoadingReviewsPassanger, reviews:reviewsPassanger } = useUserReviewsByUri(user?.reviewsPassengerUri);

  return (
      (isLoadingUser || user == undefined) ? <SpinnerComponent/> :
      <div className={styles.main_container}>
        <div className={styles.profileCard}>
          <ProfileImg src={user.imageUri} />
          <h3 className="text-center">
              {t("format.name", {
              name: user.username,
              surname: user.surname,
          })}</h3>
          <ProfileProp prop={t("profile.props.trips")} text={user.tripCount.toString()} />
          <ProfileStars
              prop={t("profile.props.rating_driver")}
              rating={user.driverRating}
          />
          <ProfileStars
              prop={t("profile.props.rating_passenger")}
              rating={user.passengerRating}
          />
        </div>

        <div className={styles.list_block}>
          {isLoadingReviewsDriver || reviewsDriver == undefined ? (<SpinnerComponent/>) : (
          <ListProfileContainer
              title={t("profile.lists.review_as_driver")}
              btn_footer_text={t("profile.lists.review_more")}
              empty_text={t("profile.lists.review_empty")}
              empty_icon={"book"}
              data={reviewsDriver}
              component_name={ShortReview}
              link={publicsReviewsPath.replace(":id", String(5))}
          />)}
          {isLoadingReviewsPassanger || reviewsPassanger == undefined ? (<SpinnerComponent/>) : (
          <ListProfileContainer
              title={t("profile.lists.review_as_passanger")}
              btn_footer_text={t("profile.lists.review_more")}
              empty_text={t("profile.lists.review_empty")}
              empty_icon={"book"}
              data={reviewsPassanger}
              component_name={ShortReview}
              link={publicsReviewsPath.replace(":id", String(5))}
          />)}
        </div>
      </div>
  );
};

export default PublicProfilePage;
