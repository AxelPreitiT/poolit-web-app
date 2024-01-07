import { ZodString } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

const emailMaxLength: number = 50;
export type EmailZodType = ZodString;

export default class EmailFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: EmailZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .isEmail()
      .hasMaxLength(emailMaxLength)
      .trim()
      .toLowerCase()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  getSchema(): EmailZodType {
    return this.schema;
  }
}
