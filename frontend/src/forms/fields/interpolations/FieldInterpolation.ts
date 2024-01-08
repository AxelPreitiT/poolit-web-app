export type FieldInterpolationValue = number | string;

abstract class FieldInterpolation {
  private key: string;
  private value: FieldInterpolationValue;

  constructor(key: string, value: number | string) {
    this.key = key;
    this.value = value;
  }

  public getKey(): string {
    return this.key;
  }

  public getValue(): number | string {
    return this.value;
  }

  public equals(other: FieldInterpolation): boolean {
    return this.key === other.key;
  }
}

export default FieldInterpolation;
