import { getToday } from "@/utils/date/today";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldDateBuilder from "./builders/FormFieldDateBuilder";
import { ZodDate, ZodNullable, ZodOptional } from "zod";
import FormField from "./FormField";

export type LastDateZodType = ZodOptional<ZodNullable<ZodDate>>;
const minDate: Date = getToday();

export default class LastDateFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: LastDateZodType;

  constructor(name: string) {
    super(name);
    const [schema, interpolations] = new FormFieldDateBuilder(name)
      .hasMinDate(minDate)
      .build();
    this.schema = schema.nullable().optional();
    this.interpolations = interpolations;
  }

  public getSchema(): LastDateZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
