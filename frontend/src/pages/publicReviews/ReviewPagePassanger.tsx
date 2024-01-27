import ProfileImg from "@/components/profile/img/ProfileImg.tsx";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfileProp from "@/components/profile/prop/ProfileProp.tsx";
import ProfileStars from "@/components/profile/stars/ProfileStars.tsx";
import {useLocation, useParams} from "react-router-dom";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent.tsx";
import usePublicUserById from "@/hooks/users/usePublicUserById.tsx";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import EmptyList from "@/components/emptyList/EmptyList.tsx";
import ShortReview from "@/components/review/shorts/ShortReview.tsx";
import useUserReviewsByUri from "@/hooks/reviews/useUserReviewsByUri.tsx";

const ReviewPageDriver = () => {
  const { t } = useTranslation();
  const params = useParams();
  const { isLoading: isLoadingUser, user: user } = usePublicUserById(params.id);

  const { search } = useLocation();
  const page = new URLSearchParams(search).get("page");
  const currentPage = page == null ? 1 : parseInt(page, 10);

  return (
    <div className={styles.main_container}>
    {(isLoadingUser || user == undefined) ? <SpinnerComponent/> :
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
      </div>}

    {(isLoadingUser || user == undefined) ? <SpinnerComponent/> :
      <div className={styles.list_block}>
        <div className={styles.list_container}>
          <div className={styles.row_data}>
            <h2>{t("profile.lists.review_as_driver")}</h2>
          </div>
          <PaginationComponent
            uri = {createPaginationUri(user.reviewsPassengerUri, currentPage , 1)}
            current_page = {currentPage}
            useFuction={useUserReviewsByUri}
            empty_component={
            <EmptyList
                text={t("profile.lists.review_as_passanger")}
                second_text={t("created_trips.more")}
                icon={"car-front-fill"}
            />}
            component_name={ShortReview}
          />
        </div>
      </div>}
    </div>
  );
};

export default ReviewPageDriver;
