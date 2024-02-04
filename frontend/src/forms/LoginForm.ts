import EmailFormField from "./fields/EmailFormField";
import PasswordFormField from "./fields/PasswordFormField";
import RememberMeFormField from "./fields/RememberMeFormField";
import Form, { InferedFormSchemaType } from "./Form";

const emailFieldName = "email";
const passwordFieldName = "password";
const rememberMeFieldName = "remember_me";

const LoginFormFields = {
  [emailFieldName]: EmailFormField,
  [passwordFieldName]: PasswordFormField,
  [rememberMeFieldName]: RememberMeFormField,
};

export type LoginFormFieldsType = typeof LoginFormFields;

export const LoginForm = new Form<LoginFormFieldsType>(LoginFormFields);

export type LoginFormSchemaType = InferedFormSchemaType<LoginFormFieldsType>;

export const LoginFormSchema = LoginForm.getSchema();
