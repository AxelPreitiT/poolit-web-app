import CircleImg from "@/components/img/circleImg/CircleImg";
import styles from "./styles.module.scss";

interface ModalMakeHeaderProps {
  title: string;
  imageSrc: string;
}

const ModalMakeHeader = ({ title, imageSrc }: ModalMakeHeaderProps) => {
  return (
    <div className={styles.reportFormHeader}>
      <div className={styles.imgCointainer}>
        <CircleImg src={imageSrc} size={70} />
      </div>
      <div className={styles.reportFormTitle}>
        <h3>{title}</h3>
        <hr></hr>
      </div>
    </div>
  );
};

export default ModalMakeHeader;
