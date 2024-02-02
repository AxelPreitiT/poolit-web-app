import {
  DecideReportForm,
  DecideReportFormSchema,
  DecideReportFormSchemaType,
} from "@/forms/DecideReportForm";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import useReportById from "../admin/useReportById";
import PrivateReportModel from "@/models/PrivateReportModel";
import useAllReports from "../admin/useAllReports";

interface DecideReportFormProps {
  report: PrivateReportModel;
  onSubmit: SubmitHandlerReturnModel<DecideReportFormSchemaType, void>;
  onSuccess: () => void;
  onError: (error: Error) => void;
}

const useDecideReportForm = ({
  report,
  onSubmit,
  onSuccess,
  onError,
}: DecideReportFormProps) => {
  const { invalidateReport } = useReportById(report.reportId.toString());
  const { invalidateAllReports } = useAllReports();

  const handleSuccess = () => {
    invalidateReport();
    invalidateAllReports();
    onSuccess();
  };

  return useForm({
    form: DecideReportForm,
    formSchema: DecideReportFormSchema,
    onSubmit,
    onSuccess: handleSuccess,
    onError,
  });
};

export default useDecideReportForm;
