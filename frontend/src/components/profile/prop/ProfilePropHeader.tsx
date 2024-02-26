import styles from "./styles.module.scss";

interface ProfilePropHeaderProps {
  prop: string;
}

const ProfilePropHeader = ({ prop }: ProfilePropHeaderProps) => {
  return <p className={styles.propHeader}>{prop}</p>;
};

export default ProfilePropHeader;
