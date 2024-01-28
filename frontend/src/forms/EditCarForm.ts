import Form, { InferedFormSchemaType } from "./Form";
import CarDescriptionFormField from "./fields/CarDescriptionFormField";
import CarFeaturesFormField from "./fields/CarFeaturesFormField";
import ImageFormField from "./fields/ImageFormField";
import SeatsFormField from "./fields/SeatsFormField";

const carDescriptionFieldName = "car_description";
const carSeatsFieldName = "seats";
const carImageFieldName = "image";
const carFeaturesFieldName = "car_features";

const EditCarFormFields = {
  [carDescriptionFieldName]: CarDescriptionFormField,
  [carSeatsFieldName]: SeatsFormField,
  [carImageFieldName]: ImageFormField,
  [carFeaturesFieldName]: CarFeaturesFormField,
};

export type EditCarFormFieldsType = typeof EditCarFormFields;

export const EditCarForm = new Form<EditCarFormFieldsType>(EditCarFormFields);

export type EditCarFormSchemaType =
  InferedFormSchemaType<EditCarFormFieldsType>;

export const EditCarFormSchema = EditCarForm.getSchema();
