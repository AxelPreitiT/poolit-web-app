import Form, { InferedFormSchemaType } from "./Form";
import AddressFormField from "./fields/AddressFormField";
import CarFormField from "./fields/CarFormField";
import CityFormField from "./fields/CityFormField";
import DateFormField from "./fields/DateFormField";
import LastDateFormField from "./fields/LastDateFormField";
import PriceFormField from "./fields/PriceFormField";
import MultitripFormField from "./fields/MultitripFormField";
import SeatsFormField from "./fields/SeatsFormField";
import TimeFormField from "./fields/TimeFormField";
import nowOrLaterRefine from "./refine/NowOrLaterRefine";
import isMultitripRefine from "./refine/IsMultitripRefine";
import endDateIsAfterStartDateRefine from "./refine/EndDateIsAfterStartDateRefine";
import sameWeekDayRefine from "./refine/SameWeekDayRefine";

const originCityFieldName = "origin_city";
const originAddressFieldName = "origin_address";
const dateFieldName = "date";
const timeFieldName = "time";
const multitripFieldName = "multitrip";
const lastDateFieldName = "last_date";
const destinationCityFieldName = "destination_city";
const destinationAddressFieldName = "destination_address";
const carFieldName = "car";
const seatsFieldName = "seats";
const priceFieldName = "price";

const CreateTripFormFields = {
  [originCityFieldName]: CityFormField,
  [originAddressFieldName]: AddressFormField,
  [dateFieldName]: DateFormField,
  [timeFieldName]: TimeFormField,
  [multitripFieldName]: MultitripFormField,
  [lastDateFieldName]: LastDateFormField,
  [destinationCityFieldName]: CityFormField,
  [destinationAddressFieldName]: AddressFormField,
  [carFieldName]: CarFormField,
  [seatsFieldName]: SeatsFormField,
  [priceFieldName]: PriceFormField,
};

export type CreateTripFormFieldsType = typeof CreateTripFormFields;

export const CreateTripForm = new Form<CreateTripFormFieldsType>(
  CreateTripFormFields
);

export type CreateTripFormSchemaType =
  InferedFormSchemaType<CreateTripFormFieldsType>;

export const CreateTripFormSchema = CreateTripForm.getSchema()
  .refine(
    nowOrLaterRefine<CreateTripFormSchemaType>(dateFieldName, timeFieldName),
    {
      message: `error.${dateFieldName}.before_now`,
      path: [dateFieldName],
    }
  )
  .refine(
    isMultitripRefine<CreateTripFormSchemaType>(
      multitripFieldName,
      lastDateFieldName
    ),
    {
      message: `error.${lastDateFieldName}.required`,
      path: [lastDateFieldName],
    }
  )
  .refine(
    endDateIsAfterStartDateRefine<CreateTripFormSchemaType>(
      dateFieldName,
      lastDateFieldName
    ),
    {
      message: `error.${lastDateFieldName}.before_initial`,
      path: [lastDateFieldName],
    }
  )
  .refine(
    sameWeekDayRefine<CreateTripFormSchemaType>(
      dateFieldName,
      lastDateFieldName
    ),
    {
      message: `error.${lastDateFieldName}.different_day`,
      path: [lastDateFieldName],
    }
  );
