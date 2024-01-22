import { ZodString, z } from "zod";
import FormFieldBuilder from "./FormFieldBuilder";
import MinValueFieldInterpolation from "../interpolations/MinValueFieldInterpolation";
import MaxValueFieldInterpolation from "../interpolations/MaxValueFieldInterpolation";

class FormFieldStringBuilder extends FormFieldBuilder<ZodString> {
  constructor(name: string) {
    super(z.string(), name);
  }

  public isRequired(): FormFieldStringBuilder {
    this.setSchema(this.schema.min, {
      value: 1,
      message: `error.${this.name}.required`,
    });
    return this;
  }

  public hasMinLength(minLength: number): FormFieldStringBuilder {
    this.setSchema(this.schema.min, {
      value: minLength,
      message: `error.${this.name}.${MinValueFieldInterpolation.KEY}`,
    });
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMinValue,
      minLength
    );
    return this;
  }

  public hasMaxLength(maxLength: number): FormFieldStringBuilder {
    this.setSchema(this.schema.max, {
      value: maxLength,
      message: `error.${this.name}.${MaxValueFieldInterpolation.KEY}`,
    });
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setMaxValue,
      maxLength
    );
    return this;
  }

  public hasRegex(regex: RegExp, format: string): FormFieldStringBuilder {
    this.setSchema(this.schema.regex, {
      value: regex,
      message: `error.${this.name}.invalid`,
    });
    this.setInterpolationsBuilder(
      this.interpolationsBuilder.setRegexFormat,
      format
    );
    return this;
  }

  public isEmail(): FormFieldStringBuilder {
    this.setSchema(this.schema.email, {
      message: `error.${this.name}.invalid`,
    });
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
