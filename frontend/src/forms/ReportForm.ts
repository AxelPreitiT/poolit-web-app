import Form, { InferedFormSchemaType } from "./Form";
import OptionFormField from "./fields/OptionFormField";
import ReportReasonFormField from "./fields/ReportReasonFormField";

const optionFieldName = "option";
const reasonFieldName = "reason";

const ReportFormFields = {
  [optionFieldName]: OptionFormField,
  [reasonFieldName]: ReportReasonFormField,
};

export type ReportFormFieldsType = typeof ReportFormFields;

export const ReportForm = new Form<ReportFormFieldsType>(ReportFormFields);

export type ReportFormSchemaType = InferedFormSchemaType<ReportFormFieldsType>;

export const ReportFormSchema = ReportForm.getSchema();
