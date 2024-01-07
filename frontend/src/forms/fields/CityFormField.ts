import { ZodEffects, ZodNumber, z } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";

export type CityZodType = ZodEffects<ZodNumber, number, unknown>;
const cityMinId: number = 1;

export default class CityFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private preSchema: ZodNumber;

  constructor(name: string) {
    super(name);
    [this.preSchema, this.interpolations] = new FormFieldNumberBuilder(name)
      .hasMinValue(cityMinId)
      .build();
  }

  public getSchema(): CityZodType {
    return z.preprocess(
      (val) => parseInt(z.string().parse(val), 10),
      this.preSchema
    );
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
