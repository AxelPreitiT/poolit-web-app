import ReportRelation from "@/enums/ReportRelation";
import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import { useQuery } from "@tanstack/react-query";
import ReportService from "@/services/ReportService";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { useEffect } from "react";

const useReportOptions = (relation: ReportRelation) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const query = useQuery({
    queryKey: ["reportOptions", relation],
    queryFn: async () => {
      return await ReportService.getReportOptionsByRelation(relation);
    },
    retry: false,
  });

  const { isError, error, data: reportOptions } = query;

  useEffect(() => {
    if (isError) {
      const title = t("report_options.error.title");
      const timeout = defaultToastTimeout;
      const customMessages = {
        [UnknownResponseError.ERROR_ID]: t("report_options.error.default"),
      };
      onQueryError({ error, title, timeout, customMessages });
    }
  }, [isError, error, t, onQueryError]);

  return {
    ...query,
    reportOptions,
  };
};

export default useReportOptions;
