import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import styles from "@/pages/created/styles.module.scss";
import ShortInfoReport from "@/components/admin/short-info-report.tsx"
import {useTranslation} from "react-i18next";
import useAllReports from "@/hooks/admin/useAllReports.tsx";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import PaginationList from "@/components/paginationList/paginationList.tsx";
import EmptyList from "@/components/emptyList/EmptyList.tsx";



//TODO como pasar reportsUri
const AdminPage = () => {
  const { isLoading: isLoadingReports, reports } = useAllReports("/reports");
  const { t } = useTranslation();


  return (
    <MainComponent>
      <MainHeader title={t("admin.title")} />
        <div className={styles.container_tab}>
          <div>
            {reports == undefined || isLoadingReports ? (
                <SpinnerComponent />
            ) : (
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
                    component_name={ShortInfoReport}
                />
            )}
          </div>
        </div>
    </MainComponent>
    );
};

export default AdminPage;
