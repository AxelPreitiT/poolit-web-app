import { ZodString } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type LastNameZodType = ZodString;
const lastNameMinLength: number = 2;
const lastNameMaxLength: number = 30;

export default class LastNameFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: LastNameZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasMinLength(lastNameMinLength)
      .hasMaxLength(lastNameMaxLength)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): LastNameZodType {
    return this.schema;
  }
}
