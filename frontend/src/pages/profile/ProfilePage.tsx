import ProfileImg from "@/components/profile/img/ProfileImg";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import StarRating from "@/components/stars/StarsRanking";
import ListContainer from "@/components/profile/list/ListContainer";

const ProfilePage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.main_container}>
      <div className={styles.profileCard}>
        <ProfileImg dim={2} src={ProfilePhoto} />
        <h3 className="text-center">{"Gaston Francois"}</h3>
        <ProfileProp prop={"Hola"} text={"pepe"} />
        <ProfileProp prop={"Hola"} text={"pepe"} />
        <ProfileProp prop={"Hola"} text={"pepe"} />
        <ProfileProp prop={"Hola"} text={"pepe"} />
        <ProfileProp prop={"Hola"} text={"pepe"} />
        <StarRating rating={3.5} text="Joselo" />
      </div>

      <div className={styles.list_block}>
        <ListContainer
          title={"hola"}
          btn_footer_text={"hola"}
          empty_text={"hola"}
          empty_icon={"hola"}
        />
      </div>
    </div>
  );
};

export default ProfilePage;
