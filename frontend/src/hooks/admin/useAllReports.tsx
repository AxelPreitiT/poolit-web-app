import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";

import PrivateReportModel from "@/models/PrivateReportModel.ts";
import reportService from "@/services/ReportService.ts";

const useAllReports = (reportsUri: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();


    const {
        isLoading,
        isError,
        data: reports,
        error,
        isPending,
    } = useQuery({
        queryKey: ["reports", reportsUri],
        queryFn: async ({ queryKey }): Promise<PrivateReportModel[]> => {
            const [, reportsUri] = queryKey;
            return await reportService.getReports(reportsUri);
        },
        retry: false,
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