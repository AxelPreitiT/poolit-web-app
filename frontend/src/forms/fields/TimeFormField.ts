import { ZodString } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type TimeZodType = ZodString;
const timeRegExp: RegExp = /^([0-1][0-9]|2[0-3]):[0-5][0-9]$/;
const timeFormat: string = "HH:mm";

export default class TimeFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: TimeZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasRegex(timeRegExp, timeFormat)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): TimeZodType {
    return this.schema;
  }
}
