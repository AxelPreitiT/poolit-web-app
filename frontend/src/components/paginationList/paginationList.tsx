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
    return data.map((item, index) => <Component key={index} {...item} />);
  };

  const props = generateItems(data, component_name);

  return (
    <div className={styles.list_container}>
      {props && props.length > 0 ? (
        <div>
          <div>
            {props.map((item, index) => (
              <div className={styles.travel_info_list} key={index}>
                {item}
              </div>
            ))}
          </div>
          {pagination_component}
        </div>
      ) : (
        <div>{empty_component}</div>
      )}
    </div>
  );
};

export default PaginationList;
