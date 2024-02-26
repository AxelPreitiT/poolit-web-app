import { ZodString } from "zod";
import FormField from "./FormField";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import FormFieldStringBuilder from "./builders/FormFieldStringBuilder";

export type CarPlateZodType = ZodString;
const carPlateRegex: RegExp =
  /^([a-zA-Z]{2}\d{3}[a-zA-Z]{2})|([a-zA-Z]{3}\d{3})$/;
const carPlateFormat: string = "AB123CD / ABC123";

export default class CarPlateFormField extends FormField {
  private interpolations: FieldInterpolation[];
  private schema: CarPlateZodType;

  constructor(name: string) {
    super(name);
    [this.schema, this.interpolations] = new FormFieldStringBuilder(name)
      .isRequired()
      .hasRegex(carPlateRegex, carPlateFormat)
      .trim()
      .build();
  }

  public getInterpolations(): FieldInterpolation[] {
    return this.interpolations;
  }

  public getSchema(): CarPlateZodType {
    return this.schema;
  }
}
