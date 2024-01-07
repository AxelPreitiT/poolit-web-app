import { ZodString, z } from "zod";
import FormFieldBuilder from "./FormFieldBuilder";
import MinValueFieldInterpolation from "../interpolations/MinValueFieldInterpolation";
import MaxValueFieldInterpolation from "../interpolations/MaxValueFieldInterpolation";

class FormFieldStringBuilder extends FormFieldBuilder<ZodString> {
  private name: string;

  constructor(name: string) {
    super(z.string());
    this.name = name;
  }

  public isRequired(): FormFieldStringBuilder {
    this.setSchema(this.schema.min, 1, `error.${this.name}.required`);
    return this;
  }

  public hasMinLength(minLength: number): FormFieldStringBuilder {
    this.setSchema(
      this.schema.min,
      minLength,
      `error.${this.name}.${MinValueFieldInterpolation.KEY}`
    );
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMinValue,
      minLength
    );
    return this;
  }

  public hasMaxLength(maxLength: number): FormFieldStringBuilder {
    this.setSchema(
      this.schema.max,
      maxLength,
      `error.${this.name}.${MaxValueFieldInterpolation.KEY}`
    );
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMaxValue,
      maxLength
    );
    return this;
  }

  public hasRegex(regex: RegExp, format: string): FormFieldStringBuilder {
    this.setSchema(this.schema.regex, regex, `error.${this.name}.invalid`);
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setRegexFormat,
      format
    );
    return this;
  }

  public isEmail(): FormFieldStringBuilder {
    this.setSchema(this.schema.email, `error.${this.name}.invalid`);
    return this;
  }

  public trim(): FormFieldStringBuilder {
    this.setSchema(this.schema.trim);
    return this;
  }

  public toLowerCase(): FormFieldStringBuilder {
    this.setSchema(this.schema.toLowerCase);
    return this;
  }
}

export default FormFieldStringBuilder;
