import { ZodString, z } from "zod";
import FormField from "./FormField";

export type ConfirmPasswordZodType = ZodString;

export default class ConfirmPasswordField extends FormField {
  constructor(name: string) {
    super(name);
  }

  getSchema(): ConfirmPasswordZodType {
    return z.string();
  }
}
