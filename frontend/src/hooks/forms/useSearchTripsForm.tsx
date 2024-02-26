import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import {
  SearchTripsForm,
  SearchTripsFormSchema,
  SearchTripsFormSchemaType,
} from "@/forms/SearchTripsForm";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import { ModelType } from "@/models/ModelType";

interface useSearchTripsFormProps<R extends ModelType> {
  initialSearch?: Partial<SearchTripsFormSchemaType>;
  submitOnMount?: boolean;
  onSubmit?: SubmitHandlerReturnModel<SearchTripsFormSchemaType, R>;
  onSuccess: ({
    result,
    data,
  }: {
    result: R;
    data: SearchTripsFormSchemaType;
  }) => void;
  onError?: (error: Error) => void;
}

const useSearchTripsForm = <R extends ModelType>({
  initialSearch,
  submitOnMount,
  onSubmit = () => Promise.resolve({} as R),
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: useSearchTripsFormProps<R>) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const onSuccess = (result: R, data: SearchTripsFormSchemaType) => {
    onSuccessProp({ result, data });
  };

  const onError = (error: Error) => {
    const title = t("search_trips.error.title");
    const timeout = defaultToastTimeout;
    const customMessages = {
      [UnknownResponseError.ERROR_ID]: "search_trips.error.default",
    };
    onQueryError({
      error,
      title,
      timeout,
      customMessages,
    });
    onErrorProp?.(error);
  };

  return useForm({
    form: SearchTripsForm,
    formSchema: SearchTripsFormSchema,
    onSubmit,
    onSuccess,
    onError,
    defaultValues: initialSearch,
    executeOnMount: submitOnMount,
  });
};

export default useSearchTripsForm;
