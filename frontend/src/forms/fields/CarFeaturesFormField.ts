import { ZodArray, ZodOptional, ZodString, z } from "zod";
import FormField from "./FormField";

export type CarFeaturesZodType = ZodOptional<ZodArray<ZodString>>;

export default class CarFeaturesFormField extends FormField {
  constructor(name: string) {
    super(name);
  }

  public getSchema(): CarFeaturesZodType {
    return z.array(z.string()).optional();
  }
}
