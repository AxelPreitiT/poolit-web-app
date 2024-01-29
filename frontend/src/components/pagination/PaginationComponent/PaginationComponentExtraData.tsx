import styles from "./styles.module.scss";
import PaginationModel from "@/models/PaginationModel.tsx";
import { useState } from "react";
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
    status: U;
    CardComponent: React.ComponentType<{ key: number; status?: U; trip: T }>; // Componente con argumentos genéricos
}

const PaginationComponentExtraData = <T, U>({
                                                            empty_component,
                                                            uri,
                                                            current_page,
                                                            useFuction,
                                                            itemsName,
                                          status,
                                          CardComponent
                                      }: PaginationComponentProps<T, U>) => {
    const { t } = useTranslation();
    const [newUri, setNewUri] = useState(uri);
    const [currentPage, setcurrentPage] = useState(current_page);
    const location = useLocation();
    const history = createBrowserHistory();
    const { isLoading: isLoadingTrips, data: pageTrips } = useFuction(newUri);


    const handlePage = (uri: string, currentPage: number) => {
        setNewUri(uri);
        setcurrentPage(currentPage);

        //PREGUNTAR SI O SI HOY EN VIDEDO
        const finalRouterBasename = routerBasename == "/" ? "" : routerBasename;
        const searchParams = new URLSearchParams(location.search);
        searchParams.set("page", currentPage.toString());
        const newUrl = `${finalRouterBasename}${
            location.pathname
        }?${searchParams.toString()}`;
        history.push(newUrl);
    };


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
            {pageTrips.data && pageTrips.data.length > 0 ? (
                <div>
                    <div>
                        {pageTrips.data.map((trip, index) => (
                            <CardComponent key={index} trip={trip} status={status} />
                        ))}
                    </div>
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

export default PaginationComponentExtraData;
