import { ZodString } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type PasswordZodType = ZodString;
const passwordMaxLength: number = 20;
const passwordMinLength: number = 3;

export default class PasswordFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: PasswordZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasMinLength(passwordMinLength)
      .hasMaxLength(passwordMaxLength)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): PasswordZodType {
    return this.schema;
  }
}
