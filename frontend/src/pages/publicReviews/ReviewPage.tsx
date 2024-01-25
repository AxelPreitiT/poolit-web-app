import ProfileImg from "@/components/profile/img/ProfileImg.tsx";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import ProfileProp from "@/components/profile/prop/ProfileProp.tsx";
import FullReviews from "@/components/review/fullList/fullReviews.tsx";
import ShortReview from "@/components/review/shorts/ShortReview.tsx";
import ProfileStars from "@/components/profile/stars/ProfileStars.tsx";

const ReviewPage = () => {
  const { t } = useTranslation();

  const user = {
    id: 3,
    name: "Gaston Francois",
    email: "gfrancois@itba.edu.ar",
    phone: "3424394741",
    neighborhood: "Balvanera",
    language: "Español",
    trips: "5",
    rating_driver: 3.5,
    rating_passenger: 1.5,
  };

  const data2 : ReviewModel[] = [
    {
      comment: "comment",
      id:1,
      option: "type",
      rating: 2,
      reviewDateTime: "Date",
      reviewedUserUri: "Uri",
      selfUri: "Uri",
      tripUri: "Uri",
    }
  ];

  return (
    <div className={styles.main_container}>
      <div className={styles.profileCard}>
        <ProfileImg src={ProfilePhoto} />
        <h3 className="text-center">{user.name}</h3>
        <ProfileProp prop={t("profile.props.trips")} text={user.trips} />
        <ProfileStars
          prop={t("profile.props.rating_driver")}
          rating={user.rating_driver}
        />
        <ProfileStars
          prop={t("profile.props.rating_passenger")}
          rating={user.rating_passenger}
        />
      </div>

      <div className={styles.list_block}>
        <FullReviews
          title={t("profile.lists.review_as_driver")}
          empty_text={t("profile.lists.review_empty")}
          empty_icon={"book"}
          data={data2}
          component_name={ShortReview}
        />
      </div>
    </div>
  );
};

export default ReviewPage;
