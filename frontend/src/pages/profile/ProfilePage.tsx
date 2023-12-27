import ProfileImg from "@/components/profile/img/ProfileImg";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ListContainer from "@/components/profile/list/ListContainer";
import ShortReview from "@/components/review/shorts/ShortReview";
import ProfileStars from "@/components/profile/stars/ProfileStars";

const ProfilePage = () => {
  const { t } = useTranslation();

  const data = [];

  const data2 = [
    {
      type: "type",
      comment: "comment",
      raiting: 2,
      formattedDate: "Date",
    },
    {
      type: "type",
      comment: "comment",
      raiting: 2,
      formattedDate: "Date",
    },
  ];

  return (
    <div className={styles.main_container}>
      <div className={styles.profileCard}>
        <ProfileImg dim={2} src={ProfilePhoto} />
        <h3 className="text-center">{"Gaston Francois"}</h3>
        <ProfileProp prop={t("profile.props.email")} text={"pepe"} />
        <ProfileProp prop={t("profile.props.phone")} text={"pepe"} />
        <ProfileProp prop={t("profile.props.neighborhood")} text={"pepe"} />
        <ProfileProp prop={t("profile.props.language")} text={"pepe"} />
        <ProfileProp prop={t("profile.props.trips")} text={"pepe"} />
        <ProfileStars prop={t("profile.props.rating_driver")} rating={3.5} />
        <ProfileStars prop={t("profile.props.rating_passenger")} rating={1.5} />
      </div>

      <div className={styles.list_block}>
        <ListContainer
          title={"hola"}
          btn_footer_text={"hola"}
          empty_text={"hola"}
          empty_icon={"book"}
          data={data}
          component_name={ProfileProp}
        />
        <ListContainer
          title={"hola"}
          btn_footer_text={"hola"}
          empty_text={"hola"}
          empty_icon={"hola"}
          data={data2}
          component_name={ShortReview}
        />
      </div>
    </div>
  );
};

export default ProfilePage;
