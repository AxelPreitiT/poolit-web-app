import ProfileProp from "../prop/ProfileProp";
import styles from "./styles.module.scss";

interface ListContainerProps<T> {
  title: string;
  btn_footer_text: string;
  empty_text: string;
  empty_icon: string;
  data: T[];
  component_name: React.FC<T>;
}

const ListContainer = <T,>({
  title,
  btn_footer_text,
  empty_text,
  empty_icon,
  data,
  component_name,
}: ListContainerProps<T>) => {
  const generateItems = <T,>(data: T[], Component: React.FC<T>) => {
    return data.map((item, index) => <Component key={index} {...item} />);
  };

  const props = generateItems(data, component_name);

  return (
    <div className={styles.list_container}>
      <div className={styles.row_data}>
        <h2>{title}</h2>
      </div>
      <h2>{btn_footer_text}</h2>
      <h2>{empty_text}</h2>
      <h2>{empty_icon}</h2>
      <ul>
        {props.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
      <div className={styles.travel_info_list}>
        <h2>{btn_footer_text}</h2>
      </div>
    </div>
  );
};

export default ListContainer;
