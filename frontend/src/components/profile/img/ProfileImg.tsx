import styles from "./styles.module.scss";

interface ProfileImgProps {
  children: React.ReactNode;
}

const ProfileImg = ({ children }: ProfileImgProps) => (
  <div className={styles.avatar_img}>
    <div className={styles.circular__landscape}>{children}</div>
  </div>
);

export default ProfileImg;
