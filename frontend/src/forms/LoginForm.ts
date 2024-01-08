import EmailFormField from "./fields/EmailFormField";
import PasswordFormField from "./fields/PasswordFormField";
import RememberMeFormField from "./fields/RememberMeFormField";
import Form, { InferedFormSchemaType } from "./Form";

const emailFieldName = "email";
const passwordFieldName = "password";
const rememberMeFieldName = "rememberMe";

const LoginFormFields = {
  [emailFieldName]: EmailFormField,
  [passwordFieldName]: PasswordFormField,
  [rememberMeFieldName]: RememberMeFormField,
};

export type LoginFormFieldsType = typeof LoginFormFields;

export const LoginForm = new Form<LoginFormFieldsType>(LoginFormFields);

export const LoginFormSchema = LoginForm.getSchema();

export type LoginFormSchemaType = InferedFormSchemaType<LoginFormFieldsType>;
