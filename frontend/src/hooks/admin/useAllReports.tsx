import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";

import PrivateReportModel from "@/models/PrivateReportModel.ts";
import reportService from "@/services/ReportService.ts";
import PaginationModel from "@/models/PaginationModel.tsx";


const useAllReports = (uri?: string) => {
    const { t } = useTranslation();
    const onQueryError = useQueryError();


    const {
        isLoading,
        isError,
        data: reports,
        error,
        isPending,
    } = useQuery({
        queryKey: ["reports", uri],
        queryFn: async (): Promise<PaginationModel<PrivateReportModel>> => {

            return await reportService.getReports(uri as string);
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
        data: reports,
    };
};

export default useAllReports;