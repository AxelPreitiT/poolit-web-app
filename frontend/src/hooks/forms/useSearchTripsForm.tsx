import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";
import useQueryError from "../errors/useQueryError";
import useDiscovery from "../discovery/useDiscovery";
import {
  SearchTripsForm,
  SearchTripsFormSchema,
  SearchTripsFormSchemaType,
} from "@/forms/SearchTripsForm";
import TripModel from "@/models/TripModel";
import DiscoveryMissingError from "@/errors/DiscoveryMissingError";
import TripsService from "@/services/TripsService";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import { searchPath } from "@/AppRouter";
import { defaultToastTimeout } from "@/components/toasts/ToastProps";
import UnknownResponseError from "@/errors/UnknownResponseError";
import { getIsoDate } from "@/utils/date/isoDate";

const useSearchTripsForm = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const onQueryError = useQueryError();
  const { discovery, isError: isDiscoveryError } = useDiscovery();

  const onSubmit: SubmitHandlerReturnModel<
    SearchTripsFormSchemaType,
    TripModel[]
  > = async (data: SearchTripsFormSchemaType) => {
    if (!discovery || isDiscoveryError) {
      throw new DiscoveryMissingError();
    }
    return await TripsService.searchTrips(discovery.tripsUriTemplate, data);
  };

  const onSuccess = (trips: TripModel[], data: SearchTripsFormSchemaType) => {
    const searchParams = new URLSearchParams({
      origin_city: data.origin_city.toString(),
      destination_city: data.destination_city.toString(),
      date: getIsoDate(data.date),
      time: data.time.toString(),
    });
    if (data.multitrip && data.last_date) {
      searchParams.set("last_date", getIsoDate(data.last_date));
    }
    if (data.min_price) {
      searchParams.set("min_price", data.min_price.toString());
    }
    if (data.max_price) {
      searchParams.set("max_price", data.max_price.toString());
    }
    if (data.car_features) {
      data.car_features.forEach((feature) => {
        searchParams.append("car_features", feature.toString());
      });
    }
    navigate(`${searchPath}?${searchParams.toString()}`, { state: { trips } });
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
  };

  return useForm({
    form: SearchTripsForm,
    formSchema: SearchTripsFormSchema,
    onSubmit,
    onSuccess,
    onError,
  });
};

export default useSearchTripsForm;
