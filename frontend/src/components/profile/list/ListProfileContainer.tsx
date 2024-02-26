import styles from "./styles.module.scss";
import { Link } from "react-router-dom";

interface ListProfileContainerProps<T> {
  title: string | undefined;
  btn_footer_text: string;
  empty_text: string;
  empty_icon: string;
  data: T[];
  component_name: React.FC<T>;
  link: string;
}

const ListProfileContainer = <T,>({
  title,
  btn_footer_text,
  empty_text,
  empty_icon,
  data,
  component_name,
  link,
}: ListProfileContainerProps<T>) => {
  const generateItems = <T,>(data: T[], Component: React.FC<T>) => {
    if (data.length === 0) {
      return null; // O cualquier otra cosa que desees devolver en este caso
    }
    const items: JSX.Element[] = [];

    for (let index = 0; index < data.length; index++) {
      const item = <Component key={index} {...data[index]} />;
      items.push(item);
    }

    return items;
  };

  const props = generateItems(data, component_name);

  return (
    <div className={styles.list_container}>
      <div className={styles.row_data}>
        <h2>{title}</h2>
      </div>
      {!props ? (
        <div className={styles.review_empty_container}>
          <i className={`bi-solid bi-${empty_icon} h2`}></i>
          <h3 className="italic-text placeholder-text">{empty_text}</h3>
        </div>
      ) : (
        <div>
          <div className={styles.list_prop_container}>{props}</div>
          <Link to={link} className={styles.logoContainer}>
            <div className={styles.plus_btn}>
              <h3 className="text">{btn_footer_text}</h3>
            </div>
          </Link>
        </div>
      )}
      <div></div>
    </div>
  );
};

export default ListProfileContainer;
