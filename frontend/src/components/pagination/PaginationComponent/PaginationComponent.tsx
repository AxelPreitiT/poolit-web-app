import styles from "./styles.module.scss";
import PaginationModel from "@/models/PaginationModel.tsx";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import { routerBasename } from "@/AppRouter.tsx";
import { createBrowserHistory } from "history";
import { ButtonGroup, ToggleButton } from "react-bootstrap";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";

interface PaginationComponentProps<T> {
  empty_component: React.ReactNode;
  uri: string;
  current_page: number;
  component_name: React.FC<T>;
  useFuction: (uri?: string) => {
    isLoading: boolean;
    data: PaginationModel<T> | undefined;
  };
}

const PaginationComponent = <T,>({
  empty_component,
  uri,
  component_name,
  current_page,
  useFuction,
}: PaginationComponentProps<T>) => {
  const [newUri, setNewUri] = useState(uri);
  const [currentPage, setcurrentPage] = useState(current_page);
  const location = useLocation();
  const history = createBrowserHistory();
  const { isLoading: isLoadingTrips, data: pageTrips } = useFuction(newUri);

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

  const props =
    isLoadingTrips || pageTrips == undefined
      ? generateItems([], component_name)
      : generateItems(pageTrips.data, component_name);

  return isLoadingTrips || pageTrips === undefined ? (
    <SpinnerComponent />
  ) : (
    <div className={styles.list_container}>
      {props && props.length > 0 ? (
        <div>
          <div>{props}</div>
          {pageTrips.totalPages > 1 && (
            <div className={styles.pagination_btn_container}>
              <ButtonGroup className="mb-2">
                <ToggleButton
                  id={`radio-pepe2`}
                  value="pepe2"
                  className={styles.btn_pagination}
                  onClick={() => handlePage(pageTrips?.first, 1)}
                  disabled={pageTrips.prev == null}
                >
                  <i className="bi bi-chevron-double-left"></i>
                </ToggleButton>
                {pageTrips.prev != null && (
                  <ToggleButton
                    id={`radio-pepe2`}
                    value="pepe2"
                    className={styles.btn_pagination}
                    disabled={true}
                  >
                    <i className="bi bi-three-dots"></i>
                  </ToggleButton>
                )}
                {pageTrips.prev != null && (
                  <ToggleButton
                    id={`radio-pepe`}
                    value="pepe"
                    className={styles.btn_pagination}
                    onClick={() => handlePage(pageTrips?.prev, currentPage - 1)}
                  >
                    {(currentPage - 1).toString()}
                  </ToggleButton>
                )}
                <ToggleButton
                  id={`radio-pepe`}
                  value="pepe"
                  variant="warning"
                  className={styles.btn_pagination}
                >
                  {currentPage.toString()}
                </ToggleButton>
                {pageTrips.next != null && (
                  <ToggleButton
                    id={`radio-pepe`}
                    value="pepe"
                    className={styles.btn_pagination}
                    onClick={() => handlePage(pageTrips?.next, currentPage + 1)}
                  >
                    {(currentPage + 1).toString()}
                  </ToggleButton>
                )}
                {pageTrips.next != null && (
                  <ToggleButton
                    id={`radio-pepe2`}
                    value="pepe2"
                    disabled={true}
                    className={styles.btn_pagination}
                  >
                    <i className="bi bi-three-dots"></i>
                  </ToggleButton>
                )}
                <ToggleButton
                  id={`radio-pepe2`}
                  value="pepe2"
                  className={styles.btn_pagination}
                  disabled={pageTrips.next == null}
                  onClick={() =>
                    handlePage(pageTrips?.last, pageTrips?.totalPages)
                  }
                >
                  <i className="bi bi-chevron-double-right"></i>
                </ToggleButton>
              </ButtonGroup>
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
