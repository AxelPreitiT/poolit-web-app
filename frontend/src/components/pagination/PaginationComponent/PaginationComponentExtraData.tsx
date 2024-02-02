import styles from "./styles.module.scss";
import PaginationModel from "@/models/PaginationModel.tsx";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { routerBasename } from "@/AppRouter.tsx";
import { createBrowserHistory } from "history";
import { Pagination } from "react-bootstrap";
import LoadingWheel from "@/components/loading/LoadingWheel";
import { useTranslation } from "react-i18next";

interface PaginationComponentProps<T, U> {
  empty_component: React.ReactNode;
  uri: string;
  current_page: number;
  useFuction: (uri?: string) => {
    isLoading: boolean;
    data: PaginationModel<T> | undefined;
  };
  itemsName: string;
  CardComponent: React.ComponentType<{ key: number; extraData?: U; data: T }>; // Componente con argumentos genéricos
  extraData?: U;
}

const PaginationComponentExtraData = <T, U>({
  empty_component,
  uri,
  current_page,
  useFuction,
  itemsName,
  extraData,
  CardComponent,
}: PaginationComponentProps<T, U>) => {
  const { t } = useTranslation();
  const [newUri, setNewUri] = useState(uri);
  const [currentPage, setcurrentPage] = useState(current_page);
  const location = useLocation();
  const history = createBrowserHistory();
  const { isLoading: isLoadingTrips, data: FullData } = useFuction(newUri);

  useEffect(() => {
    // Este efecto se ejecutará cada vez que la prop 'uri' cambie
    setNewUri(uri);
  }, [uri]);

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

  return isLoadingTrips || FullData === undefined ? (
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
      {FullData.data && FullData.data.length > 0 ? (
        <div>
          <div>
            {FullData.data.map((data, index) => (
              <CardComponent key={index} data={data} extraData={extraData} />
            ))}
          </div>
          {FullData.totalPages > 1 && (
            <div className={styles.pagination_btn_container}>
              <Pagination className={styles.paginationContainer}>
                <Pagination.First
                  as="button"
                  className={styles.paginationItem}
                  onClick={() => handlePage(FullData?.first, 1)}
                  disabled={!FullData.prev}
                />
                <Pagination.Prev
                  as="button"
                  className={styles.paginationItem}
                  onClick={() => handlePage(FullData?.prev, currentPage - 1)}
                  disabled={!FullData.prev}
                />
                {currentPage > 2 && (
                  <Pagination.Ellipsis
                    as="button"
                    className={styles.paginationItem}
                    disabled={true}
                  />
                )}
                {FullData.prev != null && (
                  <Pagination.Item
                    as="button"
                    className={styles.paginationItem}
                    onClick={() => handlePage(FullData?.prev, currentPage - 1)}
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
                {FullData.next != null && (
                  <Pagination.Item
                    as="button"
                    className={styles.paginationItem}
                    onClick={() => handlePage(FullData?.next, currentPage + 1)}
                  >
                    {(currentPage + 1).toString()}
                  </Pagination.Item>
                )}
                {currentPage < FullData.totalPages - 1 && (
                  <Pagination.Ellipsis
                    as="button"
                    className={styles.paginationItem}
                    disabled={true}
                  />
                )}
                <Pagination.Next
                  as="button"
                  className={styles.paginationItem}
                  onClick={() => handlePage(FullData?.next, currentPage + 1)}
                  disabled={!FullData.next}
                />
                <Pagination.Last
                  as="button"
                  className={styles.paginationItem}
                  disabled={!FullData.next}
                  onClick={() =>
                    handlePage(FullData?.last, FullData?.totalPages)
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

export default PaginationComponentExtraData;
