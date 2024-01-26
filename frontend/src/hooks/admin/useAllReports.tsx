import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";

import PrivateReportModel from "@/models/PrivateReportModel.ts";
import reportService from "@/services/ReportService.ts";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";

//TODO Agregar al discovery /reports/
const useAllReports = () => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();
    const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();

    //TODO reports en discovery
    const {
        isLoading,
        isError,
        data: reports,
        error,
        isPending,
    } = useQuery({
        queryKey: ["reports"],
        queryFn: async (): Promise<PrivateReportModel[]> => {
            if (!discovery?.reportsUriTemplate) {
                return [];
            }
            return await reportService.getReports("/reports/");
        },
        retry: false,
        enabled: !isLoadingDiscovery && !!discovery?.reportsUriTemplate,
    });

    useEffect(() => {
        if (isError) {
            onQueryError({
                error,
                title: t("admin.error.title"),
                timeout: defaultToastTimeout,
            });
        }
    }, [isError, error, onQueryError, t]);

    return {
        isLoading: isLoading || isPending,
        reports,
    };
};

export default useAllReports;