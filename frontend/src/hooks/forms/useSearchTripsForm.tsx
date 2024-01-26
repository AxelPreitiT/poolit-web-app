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

interface useSearchTripsFormProps {
  initialSearch?: Partial<SearchTripsFormSchemaType>;
  onSubmit: SubmitHandlerReturnModel<SearchTripsFormSchemaType, TripModel[]>;
  onSuccess: ({
    trips,
    data,
  }: {
    trips: TripModel[];
    data: SearchTripsFormSchemaType;
  }) => void;
  onError?: (error: Error) => void;
}

const useSearchTripsForm = ({
  initialSearch,
  onSubmit,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: useSearchTripsFormProps) => {
  const { t } = useTranslation();
  const onQueryError = useQueryError();

  const onSuccess = (trips: TripModel[], data: SearchTripsFormSchemaType) => {
    onSuccessProp({ trips, data });
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
  });
};

export default useSearchTripsForm;
