import Form, { InferedFormSchemaType } from "./Form";
import CityFormField from "./fields/CityFormField";
import ImageFormField from "./fields/ImageFormField";
import LastNameFormField from "./fields/LastNameFormField";
import LocaleFormField from "./fields/LocaleFormField";
import NameFormField from "./fields/NameFormField";
import TelephoneFormField from "./fields/TelephoneFormField";

const imageFieldName = "image";
const nameFieldName = "name";
const lastNameFieldName = "last_name";
const telephoneFieldName = "telephone";
const cityFieldName = "city";
const localeFieldName = "locale";

const EditProfileFormFields = {
  [imageFieldName]: ImageFormField,
  [nameFieldName]: NameFormField,
  [lastNameFieldName]: LastNameFormField,
  [telephoneFieldName]: TelephoneFormField,
  [cityFieldName]: CityFormField,
  [localeFieldName]: LocaleFormField,
};

export type EditProfileFormFieldsType = typeof EditProfileFormFields;

export const EditProfileForm = new Form<EditProfileFormFieldsType>(
  EditProfileFormFields
);

export type EditProfileFormSchemaType =
  InferedFormSchemaType<EditProfileFormFieldsType>;

export const EditProfileFormSchema = EditProfileForm.getSchema();
