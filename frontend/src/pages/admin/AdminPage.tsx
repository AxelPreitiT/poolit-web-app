import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import styles from "@/pages/admin/styles.module.scss";
import ShortInfoReport from "@/components/admin/ShortInfoReport";
import { useTranslation } from "react-i18next";
import useAllReports from "@/hooks/admin/useAllReports.tsx";
import PaginationList from "@/components/paginationList/paginationList.tsx";
import EmptyList from "@/components/emptyList/EmptyList.tsx";
import LoadingScreen from "@/components/loading/LoadingScreen";

//TODO como pasar reportsUri
const AdminPage = () => {
  const { isLoading: isLoadingReports, reports } = useAllReports();
  const { t } = useTranslation();

  if (isLoadingReports || reports === undefined) {
    return <LoadingScreen description={t("admin.loading")} />;
  }

  return (
    <MainComponent>
      <MainHeader title={t("admin.title")} />
      <div className={styles.container_tab}>
        <div>
          {/* TODO: Use PaginationComponent */}
          <PaginationList
            pagination_component={<h3>Poner paginación</h3>}
            empty_component={
              <EmptyList
                text={t("admin.empty")}
                second_text={t("")}
                icon={"megaphone-fill"}
              />
            }
            data={reports}
            Item={ShortInfoReport}
          />
        </div>
      </div>
    </MainComponent>
  );
};

export default AdminPage;
