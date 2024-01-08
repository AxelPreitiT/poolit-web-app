import { ZodType, ZodTypeDef, z } from "zod";
import FieldInterpolation from "./interpolations/FieldInterpolation";
import MaxValueFieldInterpolation from "./interpolations/MaxValueFieldInterpolation";
import FormField from "./FormField";
import FieldInterpolationsBuilder from "./interpolations/FieldInterpolationsBuilder";

export type ImageZodType = ZodType<File, ZodTypeDef, File>;

const mbSize: number = 1024 * 1024;
const imageMaxSize: number = 10 * mbSize; // 10 MB

export default class ImageFormField extends FormField {
  constructor(name: string) {
    super(name);
  }

  public getInterpolations(): FieldInterpolation[] {
    return new FieldInterpolationsBuilder().setMaxValue(imageMaxSize).build();
  }

  getSchema(): ImageZodType {
    return z.custom<File>(
      (file) => {
        return !file || (file instanceof File && file.size <= imageMaxSize);
      },
      {
        message: `error.${this.getName()}.${MaxValueFieldInterpolation.KEY}`,
      }
    );
  }
}
