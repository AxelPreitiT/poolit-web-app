import { ZodString } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type TelephoneZodType = ZodString;
const telephoneRegExp: RegExp = /^(\+\d{1,3}\s?)?\d{2,4}\s?\d{4}\s?\d{4}$/;
const telephoneFormat: string = "+XX XXX XXXX XXXX";

export default class TelephoneFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: TelephoneZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasRegex(telephoneRegExp, telephoneFormat)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): TelephoneZodType {
    return this.schema;
  }
}
