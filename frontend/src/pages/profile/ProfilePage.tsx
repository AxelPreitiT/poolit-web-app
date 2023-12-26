import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";

const ProfilePage = () => {
  const { t } = useTranslation();

  return <h6>{"Hola mundo"}</h6>;
};

export default ProfilePage;
