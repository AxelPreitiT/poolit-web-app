import Image from "react-bootstrap/Image";
import styles from "./styles.module.scss";
import { Link } from "react-router-dom";

interface ProfileImgProps {
  src: string;
  size: number;
  path: string;
}

const CircleImg = ({ src, size, path }: ProfileImgProps) => (
  <Link to={path}>
    <div className={styles.avatar_img}>
      <div
        className={styles.circular__landscape}
        style={{ width: size, height: size }}
      >
        <Image src={src} roundedCircle />
      </div>
    </div>
  </Link>
);

export default CircleImg;
