import Form, { InferedFormSchemaType } from "./Form";
import ReportReasonFormField from "./fields/ReportReasonFormField";

const reasonFieldName = "reason";

const DecideReportFormFields = {
  [reasonFieldName]: ReportReasonFormField,
};

export type DecideReportFormFieldsType = typeof DecideReportFormFields;

export const DecideReportForm = new Form<DecideReportFormFieldsType>(
  DecideReportFormFields
);

export type DecideReportFormSchemaType =
  InferedFormSchemaType<DecideReportFormFieldsType>;

export const DecideReportFormSchema = DecideReportForm.getSchema();
