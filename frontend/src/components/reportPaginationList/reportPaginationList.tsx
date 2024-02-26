import styles from "./styles.module.scss";

interface ReportPaginationListProps<T> {
  pagination_component: React.ReactNode;
  empty_component: React.ReactNode;
  data: T[];
  Item: React.FC<T>;
  listClassName?: string;
  itemClassName?: string;
}

const ReportPaginationList = <T,>({
  pagination_component,
  empty_component,
  data,
  Item,
  listClassName,
  itemClassName,
}: ReportPaginationListProps<T>) => {
  if (!data || data.length === 0) {
    return empty_component;
  }

  return (
    <div className={styles.paginationContainer}>
      <div className={listClassName}>
        {data.map((item, index) => (
          <Item key={index} {...item} className={itemClassName} />
        ))}
      </div>
      {pagination_component}
    </div>
  );
};

export default ReportPaginationList;
