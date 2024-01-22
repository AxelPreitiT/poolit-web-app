import { ZodString } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type NameZodType = ZodString;
const nameMinLength: number = 2;
const nameMaxLength: number = 20;

export default class NameFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: NameZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasMinLength(nameMinLength)
      .hasMaxLength(nameMaxLength)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): NameZodType {
    return this.schema;
  }
}
