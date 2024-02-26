import { ZodNumber } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";

export type RatingZodType = ZodNumber;
const minValue = 1;
const maxValue = 5;

export default class RatingFormField extends FormField {
  private schema: RatingZodType;
  private interpolations: FieldInterpolation[];

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldNumberBuilder(name)
      .hasMinValue(minValue)
      .hasMaxValue(maxValue)
      .build();
  }

  public getSchema(): RatingZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
