import styles from "./styles.module.scss";
import PaginationModel from "@/models/PaginationModel.tsx";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { routerBasename } from "@/AppRouter.tsx";
import { createBrowserHistory } from "history";
import { Pagination } from "react-bootstrap";
import LoadingWheel from "@/components/loading/LoadingWheel";
import { useTranslation } from "react-i18next";

interface PaginationComponentProps<T> {
  empty_component: React.ReactNode;
  uri: string;
  current_page: number;
  component_name: React.FC<T>;
  useFuction: (uri?: string) => {
    isLoading: boolean;
    data: PaginationModel<T> | undefined;
  };
  itemsName: string;
}

const PaginationComponent = <T,>({
  empty_component,
  uri,
  component_name,
  current_page,
  useFuction,
  itemsName,
}: PaginationComponentProps<T>) => {
  const { t } = useTranslation();
  const [newUri, setNewUri] = useState(uri);
  const [currentPage, setcurrentPage] = useState(current_page);
  const location = useLocation();
  const history = createBrowserHistory();
  const { isLoading: isLoadingTrips, data: pageTrips } = useFuction(newUri);

  const generateItems = <T,>(data: T[], Component: React.FC<T>) => {
    const items = [];
    if (data) {
      for (let i = 0; i < data.length; i++) {
        const item = <Component key={i} {...data[i]} />;
        items.push(
          <div className={styles.travel_info_list} key={i}>
            {item}
          </div>
        );
      }
      return items;
    } else {
      return null;
    }
  };

  const handlePage = (uri: string, currentPage: number) => {
    setNewUri(uri);
    setcurrentPage(currentPage);

    const finalRouterBasename = routerBasename === "/" ? "" : routerBasename;
    const searchParams = new URLSearchParams(location.search);
    searchParams.set("page", currentPage.toString());
    const newUrl = `${finalRouterBasename}${
      location.pathname
    }?${searchParams.toString()}`;
    history.push(newUrl);
  };

  const props =
    isLoadingTrips || pageTrips === undefined
      ? generateItems([], component_name)
      : generateItems(pageTrips.data, component_name);

  return isLoadingTrips || pageTrips === undefined ? (
    <LoadingWheel
      containerClassName={styles.loadingContainer}
      iconClassName={styles.loadingIcon}
      descriptionClassName={styles.loadingDescription}
      description={t("loading.items", {
        items: itemsName.toLowerCase(),
      })}
    />
  ) : (
    <div className={styles.list_container}>
      {props && props.length > 0 ? (
        <div>
          <div>{props}</div>
          {pageTrips.totalPages > 1 && (
            <div className={styles.pagination_btn_container}>
              <Pagination className={styles.paginationContainer}>
                <Pagination.First
                  as="button"
                  className={styles.paginationItem}
                  onClick={() => handlePage(pageTrips?.first, 1)}
                  disabled={!pageTrips.prev}
                />
                <Pagination.Prev
                  as="button"
                  className={styles.paginationItem}
                  onClick={() => handlePage(pageTrips?.prev, currentPage - 1)}
                  disabled={!pageTrips.prev}
                />
                {currentPage > 2 && (
                  <Pagination.Ellipsis
                    as="button"
                    className={styles.paginationItem}
                    disabled={true}
                  />
                )}
                {pageTrips.prev != null && (
                  <Pagination.Item
                    as="button"
                    className={styles.paginationItem}
                    onClick={() => handlePage(pageTrips?.prev, currentPage - 1)}
                  >
                    {(currentPage - 1).toString()}
                  </Pagination.Item>
                )}
                <Pagination.Item
                  as="button"
                  className={styles.paginationActiveItem}
                >
                  {currentPage.toString()}
                </Pagination.Item>
                {pageTrips.next != null && (
                  <Pagination.Item
                    as="button"
                    className={styles.paginationItem}
                    onClick={() => handlePage(pageTrips?.next, currentPage + 1)}
                  >
                    {(currentPage + 1).toString()}
                  </Pagination.Item>
                )}
                {currentPage < pageTrips.totalPages - 1 && (
                  <Pagination.Ellipsis
                    as="button"
                    className={styles.paginationItem}
                    disabled={true}
                  />
                )}
                <Pagination.Next
                  as="button"
                  className={styles.paginationItem}
                  onClick={() => handlePage(pageTrips?.next, currentPage + 1)}
                  disabled={!pageTrips.next}
                />
                <Pagination.Last
                  as="button"
                  className={styles.paginationItem}
                  disabled={!pageTrips.next}
                  onClick={() =>
                    handlePage(pageTrips?.last, pageTrips?.totalPages)
                  }
                />
              </Pagination>
            </div>
          )}
        </div>
      ) : (
        <div>{empty_component}</div>
      )}
    </div>
  );
};

export default PaginationComponent;
