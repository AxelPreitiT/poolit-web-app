import { ZodDate } from "zod";
import FormField from "./FormField";
import FormFieldDateBuilder from "./builders/FormFieldDateBuilder";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import { getToday } from "@/utils/date/today";

export type DateZodType = ZodDate;
const minDate: Date = getToday();

export default class DateFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: DateZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldDateBuilder(name)
      .hasMinDate(minDate)
      .build();
  }

  public getSchema(): DateZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
