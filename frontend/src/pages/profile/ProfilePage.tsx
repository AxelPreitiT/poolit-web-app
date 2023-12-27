import ProfileImg from "@/components/img/ProfileImg";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";

const ProfilePage = () => {
  const { t } = useTranslation();

  return (
    <div className={styles.main_container}>
      <div className={styles.profileCard}>
        <div className={styles.avatar_img}>
          <ProfileImg dim={2} src={ProfilePhoto} />
        </div>
        <h6>{t("profile.default")}</h6>
      </div>
      <div className={styles.list_block}>
        <div className={styles.list_container}>
          <h6>{t("profile.default")}</h6>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
