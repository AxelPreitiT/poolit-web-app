import { ZodNumber } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";

export type SeatsZodType = ZodNumber;
const seatsMinValue: number = 1;

export default class SeatsFormField extends FormField {
  private schema: SeatsZodType;
  private interpolations: FieldInterpolation[];

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldNumberBuilder(name)
      .isRequired()
      .hasMinValue(seatsMinValue)
      .build();
  }

  public getSchema(): SeatsZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
