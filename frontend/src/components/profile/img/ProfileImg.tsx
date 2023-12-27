import Image from "react-bootstrap/Image";
import styles from "./styles.module.scss";

interface ProfileImgProps {
  src: string;
  dim: number;
}

const ProfileImg = ({ src }: ProfileImgProps) => (
  <div className={styles.avatar_img}>
    <div className={styles.circular__landscape}>
      <Image src={src} roundedCircle />
    </div>
  </div>
);

export default ProfileImg;
