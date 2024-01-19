import CityModel from "@/models/CityModel";
import { Form, FormSelectProps } from "react-bootstrap";

export const citySelectorDefaultValue = -1;

interface CitySelectorProps {
  cities: CityModel[];
  defaultOption: string;
  onChange: (value: number) => void;
}

const CitySelector = ({
  cities,
  defaultOption,
  onChange,
  ...props
}: CitySelectorProps & FormSelectProps) => (
  <Form.Select
    {...props}
    onChange={(event) => onChange(parseInt(event.target.value))}
    defaultValue={citySelectorDefaultValue}
  >
    <option value={citySelectorDefaultValue} disabled>
      {defaultOption}
    </option>
    {cities.map((city) => (
      <option key={city.id} value={city.id}>
        {city.name}
      </option>
    ))}
  </Form.Select>
);

export default CitySelector;
