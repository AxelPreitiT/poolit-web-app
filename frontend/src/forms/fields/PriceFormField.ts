import { ZodEffects, ZodNumber, z } from "zod";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";

export type PriceZodType = ZodEffects<ZodNumber, number, unknown>;
const priceMinValue: number = 0;
const commaFloatRegex = /^(\d+)(,\d+)?$/;

export default class PriceFormField extends FormField {
  private schema: ZodNumber;
  private interpolations: FieldInterpolation[];

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldNumberBuilder(name)
      .isRequired()
      .hasMinValue(priceMinValue)
      .build();
  }

  public getSchema(): PriceZodType {
    return z.preprocess((val: unknown) => {
      if (typeof val === "string" && commaFloatRegex.test(val)) {
        return val.replace(",", ".");
      }
      return val;
    }, this.schema);
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
