import Form, { InferedFormSchemaType } from "./Form";
import CarFeaturesFormField from "./fields/CarFeaturesFormField";
import CityFormField from "./fields/CityFormField";
import DateFormField from "./fields/DateFormField";
import LastDateFormField from "./fields/LastDateFormField";
import MultitripFormField from "./fields/MultitripFormField";
import OptionalPriceFormField from "./fields/OptionalPriceFormField";
import TimeFormField from "./fields/TimeFormField";
import endDateIsAfterStartDateRefine from "./refine/EndDateIsAfterStartDateRefine";
import isMultitripRefine from "./refine/IsMultitripRefine";
import nowOrLaterRefine from "./refine/NowOrLaterRefine";
import priceRefine from "./refine/PriceRefine";
import sameWeekDayRefine from "./refine/SameWeekDayRefine";

const originCityFieldName = "origin_city";
const destinationCityFieldName = "destination_city";
const dateFieldName = "date";
const timeFieldName = "time";
const multitripFieldName = "multitrip";
const lastDateFieldName = "last_date";
const minPriceFieldName = "min_price";
const maxPriceFieldName = "max_price";
const carFeaturesFieldName = "car_features";

const SearchTripsFormFields = {
  [originCityFieldName]: CityFormField,
  [destinationCityFieldName]: CityFormField,
  [dateFieldName]: DateFormField,
  [timeFieldName]: TimeFormField,
  [multitripFieldName]: MultitripFormField,
  [lastDateFieldName]: LastDateFormField,
  [minPriceFieldName]: OptionalPriceFormField,
  [maxPriceFieldName]: OptionalPriceFormField,
  [carFeaturesFieldName]: CarFeaturesFormField,
};

export type SearchTripsFormFieldsType = typeof SearchTripsFormFields;

export const SearchTripsForm = new Form<SearchTripsFormFieldsType>(
  SearchTripsFormFields
);

export type SearchTripsFormSchemaType =
  InferedFormSchemaType<SearchTripsFormFieldsType>;

export const SearchTripsFormSchema = SearchTripsForm.getSchema()
  .refine(
    nowOrLaterRefine<SearchTripsFormSchemaType>(dateFieldName, timeFieldName),
    {
      message: `error.${dateFieldName}.before_now`,
      path: [dateFieldName],
    }
  )
  .refine(
    isMultitripRefine<SearchTripsFormSchemaType>(
      multitripFieldName,
      lastDateFieldName
    ),
    {
      message: `error.${lastDateFieldName}.required`,
      path: [lastDateFieldName],
    }
  )
  .refine(
    endDateIsAfterStartDateRefine<SearchTripsFormSchemaType>(
      dateFieldName,
      lastDateFieldName
    ),
    {
      message: `error.${lastDateFieldName}.before_initial`,
      path: [lastDateFieldName],
    }
  )
  .refine(
    sameWeekDayRefine<SearchTripsFormSchemaType>(
      dateFieldName,
      lastDateFieldName
    ),
    {
      message: `error.${lastDateFieldName}.different_day`,
      path: [lastDateFieldName],
    }
  )
  .refine(
    priceRefine<SearchTripsFormSchemaType>(
      minPriceFieldName,
      maxPriceFieldName
    ),
    {
      message: `error.${maxPriceFieldName}.lower_than_min`,
      path: [maxPriceFieldName],
    }
  );
