import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import useQueryError from "../errors/useQueryError";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import { useTranslation } from "react-i18next";

import PrivateReportModel from "@/models/PrivateReportModel.ts";
import reportService from "@/services/ReportService.ts";
import useDiscovery from "@/hooks/discovery/useDiscovery.tsx";
import { parseTemplate } from "url-template";

const useReportById = (id?: string) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();
  const queryClient = useQueryClient();
  const { isLoading: isLoadingDiscovery, discovery } = useDiscovery();

  const query = useQuery({
    queryKey: ["report", id],
    queryFn: async (): Promise<PrivateReportModel | undefined> => {
      if (!id || !discovery?.usersUriTemplate) {
        return undefined;
      }
      const uri = parseTemplate(discovery.reportsUriTemplate).expand({
        reportId: id,
      });
      return await reportService.getReport(uri);
    },
    retry: false,
    enabled: !isLoadingDiscovery && !!discovery?.reportsUriTemplate && !!id,
  });

  const { isError, error, data: report, isLoading, isPending } = query;

  useEffect(() => {
    if (isError) {
      onQueryError({
        error,
        title: t("admin.error.title"),
        timeout: defaultToastTimeout,
      });
    }
  }, [isError, error, onQueryError, t]);

  const invalidateReport = () => {
    queryClient.invalidateQueries({ queryKey: ["report", id] });
  };

  return {
    ...query,
    isLoading: isLoading || isPending,
    report,
    invalidateReport,
  };
};

export default useReportById;
