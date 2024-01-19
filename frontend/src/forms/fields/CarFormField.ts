import { ZodNumber } from "zod";
import FormFieldNumberBuilder from "./builders/FormFieldNumberBuilder";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";

export type CarZodType = ZodNumber;
const carMinId: number = 1;

export default class CarFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: CarZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldNumberBuilder(name)
      .isRequired()
      .hasMinValue(carMinId)
      .build();
  }

  public getSchema(): CarZodType {
    return this.schema;
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }
}
