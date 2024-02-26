import CarBrandModel from "@/models/CarBrandModel";
import { ChangeEventHandler } from "react";
import { Form, FormSelectProps } from "react-bootstrap";
import { useTranslation } from "react-i18next";

export const carBrandSelectorDefaultValue = "";

interface CarBrandSelectorProps {
  carBrands: CarBrandModel[];
  defaultOption: string;
  onChange: ChangeEventHandler<HTMLSelectElement>;
  value: string;
}

const CarBrandSelector = ({
  carBrands,
  defaultOption,
  onChange,
  value,
  ...props
}: CarBrandSelectorProps & FormSelectProps) => {
  const { t } = useTranslation();
  const sorter = (a: CarBrandModel, b: CarBrandModel) => {
    if (!a.name) return 1;
    if (!b.name) return -1;
    return a.name.localeCompare(b.name) || a.id.localeCompare(b.id);
  };

  return (
    <Form.Select {...props} onChange={onChange} value={value}>
      <option value={carBrandSelectorDefaultValue} disabled>
        {defaultOption}
      </option>
      {carBrands.sort(sorter).map((carBrand) => (
        <option key={carBrand.id} value={carBrand.id}>
          {carBrand.name || t("car_brands.unknown")}
        </option>
      ))}
    </Form.Select>
  );
};

export default CarBrandSelector;
