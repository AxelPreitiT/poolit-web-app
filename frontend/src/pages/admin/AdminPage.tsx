import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import styles from "@/pages/admin/styles.module.scss";
import ShortInfoReport from "@/components/admin/ShortInfoReport";
import { useTranslation } from "react-i18next";
import EmptyList from "@/components/emptyList/EmptyList.tsx";
import LoadingScreen from "@/components/loading/LoadingScreen";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import {useLocation} from "react-router-dom";
import LoadingWheel from "@/components/loading/LoadingWheel.tsx";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent.tsx";
import UseAllReports from "@/hooks/admin/useAllReports.tsx";
import {parseTemplate} from "url-template";
import {INITIALPAGE, ADMINPAGESIZE} from "@/enums/PaginationConstants.ts";


const AdminPage = () => {
  const { t } = useTranslation();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();
  const { search } = useLocation();
  const page = new URLSearchParams(search).get("page");
  const currentPage = page == null ? INITIALPAGE : parseInt(page, 10);

  if ( isLoadingDiscovery) {
    return <LoadingScreen description={t("admin.loading")} />;
  }

  return (
    <MainComponent>
      <MainHeader title={t("admin.title")} />
      <div className={styles.container_tab}>
        <div>
            { discovery == undefined? (
            <LoadingWheel
                containerClassName={styles.loadingContainer}
                iconClassName={styles.loadingIcon}
                descriptionClassName={styles.loadingDescription}
                description={t("admin.loading")}
            />
            ) : (
            <PaginationComponent
                uri={
                    createPaginationUri(
                        parseTemplate(discovery.reportsUriTemplate).expand({}),
                        currentPage,
                        ADMINPAGESIZE,
                        true
                    )
                }

                current_page={currentPage}
                useFuction={UseAllReports}
                empty_component={
                    <EmptyList
                        text={t("admin.empty")}
                        second_text={t("")}
                        icon={"megaphone-fill"}
                    />
                }
                component_name={ShortInfoReport}
                itemsName={t("reviews.title")}
            />
            )}



        </div>
      </div>
    </MainComponent>
  );
};

export default AdminPage;
