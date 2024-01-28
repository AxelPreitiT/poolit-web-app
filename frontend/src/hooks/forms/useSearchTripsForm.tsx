import { useTranslation } from "react-i18next";
import useQueryError from "../errors/useQueryError";
import {
  SearchTripsForm,
  SearchTripsFormSchema,
  SearchTripsFormSchemaType,
} from "@/forms/SearchTripsForm";
import TripModel from "@/models/TripModel";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import PaginationModel from "@/models/PaginationModel";

interface useSearchTripsFormProps {
  initialSearch?: Partial<SearchTripsFormSchemaType>;
  submitOnMount?: boolean;
  onSubmit: SubmitHandlerReturnModel<
    SearchTripsFormSchemaType,
    PaginationModel<TripModel>
  >;
  onSuccess: ({
    paginatedTrips,
    data,
  }: {
    paginatedTrips: PaginationModel<TripModel>;
    data: SearchTripsFormSchemaType;
  }) => void;
  onError?: (error: Error) => void;
}

const useSearchTripsForm = ({
  initialSearch,
  submitOnMount,
  onSubmit,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: useSearchTripsFormProps) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const onSuccess = (
    paginatedTrips: PaginationModel<TripModel>,
    data: SearchTripsFormSchemaType
  ) => {
    onSuccessProp({ paginatedTrips, data });
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
