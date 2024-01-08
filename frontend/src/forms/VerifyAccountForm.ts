import Form, { InferedFormSchemaType } from "./Form";
import EmailFormField from "./fields/EmailFormField";

const emailFieldName = "email";

const VerifyAccountFormFields = {
  [emailFieldName]: EmailFormField,
};

export type VerifyAccountFormFieldsType = typeof VerifyAccountFormFields;

export const VerifyAccountForm = new Form<VerifyAccountFormFieldsType>(
  VerifyAccountFormFields
);

export const VerifyAccountFormSchema = VerifyAccountForm.getSchema();

export type VerifyAccountFormSchemaType =
  InferedFormSchemaType<VerifyAccountFormFieldsType>;
