import { ZodOptional } from "zod";
import PriceFormField, { PriceZodType } from "./PriceFormField";
import FormField from "./FormField";

type OptionalPriceZodType = ZodOptional<PriceZodType>;

export default class OptionalPriceFormField extends FormField {
  private priceFormField: PriceFormField;

  constructor(name: string) {
    super(name);
    this.priceFormField = new PriceFormField(name);
  }

  public getSchema(): OptionalPriceZodType {
    const priceSchema = this.priceFormField.getSchema();
    return priceSchema.optional();
  }

  public getInterpolations() {
    return this.priceFormField.getInterpolations();
  }
}
