export type FieldInterpolationValue = number | string;

abstract class FieldInterpolation {
  private key: string;
  private value: FieldInterpolationValue;

  constructor(key: string, value: FieldInterpolationValue) {
    this.key = key;
    this.value = value;
  }

  public getKey(): string {
    return this.key;
  }

  public getValue(): FieldInterpolationValue {
    return this.value;
  }

  public equals(other: FieldInterpolation): boolean {
    return this.key === other.key;
  }
}

export default FieldInterpolation;
