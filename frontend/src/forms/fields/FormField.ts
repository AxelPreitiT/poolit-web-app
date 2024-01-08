import { ZodType } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";

abstract class FormField {
  private name: string;

  constructor(name: string) {
    this.name = name;
  }

  public getName(): string {
    return this.name;
  }

  public getInterpolations(): FieldInterpolation[] {
    return [];
  }

  abstract getSchema(): ZodType;

  public equals(other: FormField): boolean {
    return this.name === other.name;
  }
}

export default FormField;
