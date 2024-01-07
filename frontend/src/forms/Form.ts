import { ZodObject, z } from "zod";
import FormField from "./fields/FormField";
import { FieldInterpolationValue } from "./fields/interpolations/FieldInterpolation";
import i18next from "i18next";

type FormFieldsRecordType = Record<string, { new (name: string): FormField }>;

export type FormFieldsType = Record<string, typeof FormField>;

type FormFieldSchemaReturnType<T extends typeof FormField> = ReturnType<
  InstanceType<T>["getSchema"]
>;

type FormSchemaType<T extends FormFieldsType> = ZodObject<{
  [K in keyof T]: FormFieldSchemaReturnType<T[K]>;
}>;

export type InferedFormSchemaType<T extends FormFieldsType> = z.infer<
  FormSchemaType<T>
>;

const calculateFormFields = (formFieldsRecord: FormFieldsRecordType) =>
  new Set(
    Object.entries(formFieldsRecord).map(
      ([name, FormFieldClass]) => new FormFieldClass(name)
    )
  );

const calculateInterpolations = (formFields: Set<FormField>) =>
  Object.fromEntries(
    [...formFields].map((formField) => [
      formField.getName(),
      Object.fromEntries(
        [...formField.getInterpolations()].map((interpolation) => [
          interpolation.getKey(),
          interpolation.getValue(),
        ])
      ),
    ])
  );

class Form<T extends FormFieldsType> {
  private formFields: Set<FormField>;
  private interpolations: Record<
    string,
    Record<string, FieldInterpolationValue>
  >;

  constructor(formFieldsRecord: FormFieldsRecordType) {
    this.formFields = calculateFormFields(formFieldsRecord);
    this.interpolations = calculateInterpolations(this.formFields);
  }

  public getSchema(): FormSchemaType<T> {
    return z.object(
      Object.fromEntries(
        [...this.formFields].map((formField) => [
          formField.getName(),
          formField.getSchema(),
        ])
      )
    ) as FormSchemaType<T>;
  }

  public tFormError(key?: `error.${string}`): string | undefined {
    return key && i18next.t(key, this.interpolations[key.split(".")[1]]);
  }
}

export default Form;
