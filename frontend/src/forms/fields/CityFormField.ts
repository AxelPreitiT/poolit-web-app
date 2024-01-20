import { ZodNumber } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormField from "./FormField";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";

export type CityZodType = ZodNumber;
const cityMinId: number = 1;

export default class CityFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: CityZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldNumberBuilder(name)
      .hasMinValue(cityMinId)
      .build();
  }

  public getSchema(): CityZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
