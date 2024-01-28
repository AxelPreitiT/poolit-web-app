import CityModel from "@/models/CityModel";
import { ChangeEventHandler } from "react";
import { Form, FormSelectProps } from "react-bootstrap";

export const citySelectorDefaultValue = -1;

interface CitySelectorProps {
  cities?: CityModel[];
  defaultOption: string | null;
  onChange: ChangeEventHandler<HTMLSelectElement>;
  value: number;
}

const CitySelector = ({
  cities = [],
  defaultOption,
  onChange,
  value,
  ...props
}: CitySelectorProps & FormSelectProps) => (
  <Form.Select {...props} onChange={onChange} value={value}>
    {defaultOption && (
      <option value={citySelectorDefaultValue} disabled>
        {defaultOption}
      </option>
    )}
    {cities.map((city) => (
      <option key={city.id} value={city.id}>
        {city.name}
      </option>
    ))}
  </Form.Select>
);

export default CitySelector;
