import Image from "react-bootstrap/Image";
import styles from "./styles.module.scss";

interface ListContainerProps {
  title: string;
  btn_footer_text: string;
  empty_text: string;
  empty_icon: string;
}

const ListContainer = ({
  title,
  btn_footer_text,
  empty_text,
  empty_icon,
}: ListContainerProps) => (
  <div className={styles.list_container}>
    <div className={styles.row_data}>
      <h2>{title}</h2>
    </div>
    <div className={styles.travel_info_list}>
      <h2>{title}</h2>
    </div>
  </div>
);

export default ListContainer;
