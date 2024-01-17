import styles from "./styles.module.scss";

interface PaginationListProps<T> {
  pagination_component: React.ReactNode;
  empty_component: React.ReactNode;
  data: T[];
  component_name: React.FC<T>;
}

const PaginationList = <T,>({
  pagination_component,
  empty_component,
  data,
  component_name,
}: PaginationListProps<T>) => {
  const generateItems = <T,>(data: T[], Component: React.FC<T>) => {
    const items = [];
    for (let i = 0; i < data.length; i++) {
      const item = <Component key={i} {...data[i]} />;
      items.push(
        <div className={styles.travel_info_list} key={i}>
          {item}
        </div>
      );
    }
    return items;
  };

  const props = generateItems(data, component_name);

  return (
    <div className={styles.list_container}>
      {props && props.length > 0 ? (
        <div>
          <div>{props}</div>
          {pagination_component}
        </div>
      ) : (
        <div>{empty_component}</div>
      )}
    </div>
  );
};

export default PaginationList;
