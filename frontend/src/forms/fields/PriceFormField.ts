import { ZodNumber } from "zod";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";

export type PriceZodType = ZodNumber;
const priceMinValue: number = 0;

export default class PriceFormField extends FormField {
  private schema: PriceZodType;
  private interpolations: FieldInterpolation[];

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldNumberBuilder(name)
      .hasMinValue(priceMinValue)
      .build();
  }

  public getSchema(): PriceZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
