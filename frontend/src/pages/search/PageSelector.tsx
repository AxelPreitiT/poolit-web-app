import PaginationModel from "@/models/PaginationModel";
import TripModel from "@/models/TripModel";
import { Pagination } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";

interface PageSelectorProps {
  paginatedTrips: PaginationModel<TripModel>;
  currentPage: number;
  pageSize: number;
  setCurrentPage: (page: number) => void;
}

const PageSelector = ({
  paginatedTrips,
  currentPage,
  pageSize,
  setCurrentPage,
}: PageSelectorProps) => {
  const { t } = useTranslation();
  const [firstResult, lastResult] = [
    (currentPage - 1) * pageSize + 1,
    (currentPage - 1) * pageSize +
      Math.min(pageSize, paginatedTrips.data.length),
  ];
  const totalPages = paginatedTrips.totalPages;
  return (
    <div className={styles.pageSelectorContainer}>
      <span className={styles.pageSelectorText}>
        {t("search.page_selector", {
          first_result: firstResult,
          last_result: lastResult,
          total_pages: totalPages,
          plural: totalPages > 1 ? "s" : "",
        })}
      </span>
      {totalPages > 1 && (
        <Pagination className={styles.paginationContainer}>
          {currentPage > 1 && (
            <>
              <Pagination.First
                as="button"
                className={styles.paginationItem}
                disabled={currentPage === 1}
                onClick={() => setCurrentPage(1)}
              />
              <Pagination.Prev
                as="button"
                className={styles.paginationItem}
                disabled={currentPage === 1}
                onClick={() => setCurrentPage(currentPage - 1)}
              />
              {currentPage > 2 && (
                <>
                  <Pagination.Item
                    as="button"
                    className={styles.paginationItem}
                    onClick={() => setCurrentPage(1)}
                  >
                    {1}
                  </Pagination.Item>
                  {currentPage > 3 && (
                    <Pagination.Ellipsis
                      as="button"
                      className={styles.paginationItem}
                      onClick={() => setCurrentPage(currentPage - 2)}
                    />
                  )}
                </>
              )}
              <Pagination.Item
                as="button"
                className={styles.paginationItem}
                onClick={() => setCurrentPage(currentPage - 1)}
              >
                {currentPage - 1}
              </Pagination.Item>
            </>
          )}
          <Pagination.Item as="button" className={styles.paginationActiveItem}>
            {currentPage}
          </Pagination.Item>
          {currentPage < paginatedTrips.totalPages && (
            <>
              <Pagination.Item
                as="button"
                className={styles.paginationItem}
                onClick={() => setCurrentPage(currentPage + 1)}
              >
                {currentPage + 1}
              </Pagination.Item>
              {currentPage < paginatedTrips.totalPages - 1 && (
                <>
                  {currentPage < paginatedTrips.totalPages - 2 && (
                    <Pagination.Ellipsis
                      as="button"
                      className={styles.paginationItem}
                      onClick={() => setCurrentPage(currentPage + 2)}
                    />
                  )}
                  <Pagination.Item
                    as="button"
                    className={styles.paginationItem}
                    onClick={() => setCurrentPage(currentPage + 2)}
                  >
                    {paginatedTrips.totalPages}
                  </Pagination.Item>
                </>
              )}
              <Pagination.Next
                as="button"
                className={styles.paginationItem}
                disabled={currentPage === paginatedTrips.totalPages}
                onClick={() => setCurrentPage(currentPage + 1)}
              />
              <Pagination.Last
                as="button"
                className={styles.paginationItem}
                disabled={currentPage === paginatedTrips.totalPages}
                onClick={() => setCurrentPage(paginatedTrips.totalPages)}
              />
            </>
          )}
        </Pagination>
      )}
    </div>
  );
};

export default PageSelector;
