import Image from "react-bootstrap/Image";
import styles from "./styles.module.scss";

interface ProfileImgProps {
  src: string;
  size: number;
  className?: string;
}

const CircleImg = ({ src, size, className }: ProfileImgProps) => (
  <div className={styles.avatar_img + " " + className}>
    <div
      className={styles.circular__landscape}
      style={{ width: size, height: size }}
    >
      <Image src={src} roundedCircle />
    </div>
  </div>
);

export default CircleImg;
