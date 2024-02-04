import { ZodString } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type ReportReasonZodType = ZodString;
const minLength = 10;
const maxLength = 200;

export default class ReportReasonFormField extends FormField {
  private schema: ReportReasonZodType;
  private interpolations: FieldInterpolation[];

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasMinLength(minLength)
      .hasMaxLength(maxLength)
      .build();
  }

  public getSchema(): ReportReasonZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
