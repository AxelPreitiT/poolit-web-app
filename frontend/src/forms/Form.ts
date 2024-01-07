import { ZodObject, z } from "zod";
import FormField from "./fields/FormField";
import { FieldInterpolationValue } from "./fields/interpolations/FieldInterpolation";
import i18next from "i18next";

export type FormFieldsType = Record<string, typeof FormField>;

type FormFieldSchemaReturnType<T extends typeof FormField> = ReturnType<
  InstanceType<T>["getSchema"]
>;

const calculateFormFields = (
  formFieldsRecord: Record<string, { new (name: string): FormField }>
) =>
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

  constructor(
    formFieldsRecord: Record<string, { new (name: string): FormField }> // Fix for Problem 2
  ) {
    this.formFields = calculateFormFields(formFieldsRecord);
    this.interpolations = calculateInterpolations(this.formFields);
  }

  public getSchema(): ZodObject<{
    [K in keyof T]: FormFieldSchemaReturnType<T[K]>;
  }> {
    return z.object(
      Object.fromEntries(
        [...this.formFields].map((formField) => [
          formField.getName(),
          formField.getSchema(),
        ])
      )
    ) as ZodObject<{ [K in keyof T]: FormFieldSchemaReturnType<T[K]> }>;
  }

  public tFormError(key?: `error.${string}`): string | undefined {
    return key && i18next.t(key, this.interpolations[key.split(".")[1]]);
  }
}

export default Form;
