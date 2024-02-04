import Form, { InferedFormSchemaType } from "./Form";
import CarBrandFormField from "./fields/CarBrandFormField";
import CarDescriptionFormField from "./fields/CarDescriptionFormField";
import CarFeaturesFormField from "./fields/CarFeaturesFormField";
import CarPlateFormField from "./fields/CarPlateFormField";
import ImageFormField from "./fields/ImageFormField";
import SeatsFormField from "./fields/SeatsFormField";

const carBrandFieldName = "car_brand";
const carDescriptionFieldName = "car_description";
const carPlateFieldName = "car_plate";
const carSeatsFieldName = "seats";
const carImageFieldName = "image";
const carFeaturesFieldName = "car_features";

const CreateCarFormFields = {
  [carBrandFieldName]: CarBrandFormField,
  [carDescriptionFieldName]: CarDescriptionFormField,
  [carPlateFieldName]: CarPlateFormField,
  [carSeatsFieldName]: SeatsFormField,
  [carImageFieldName]: ImageFormField,
  [carFeaturesFieldName]: CarFeaturesFormField,
};

export type CreateCarFormFieldsType = typeof CreateCarFormFields;

export const CreateCarForm = new Form<CreateCarFormFieldsType>(
  CreateCarFormFields
);

export type CreateCarFormSchemaType =
  InferedFormSchemaType<CreateCarFormFieldsType>;

export const CreateCarFormSchema = CreateCarForm.getSchema();
