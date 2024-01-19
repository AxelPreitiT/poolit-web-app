import CarModel from "@/models/CarModel";
import { Form, FormSelectProps } from "react-bootstrap";

export const carSelectorDefaultValue = -1;

interface CarSelectorProps {
  cars: CarModel[];
  defaultOption: string;
  onChange: (value: number) => void;
}

const CarSelector = ({
  cars,
  defaultOption,
  onChange,
  ...props
}: CarSelectorProps & FormSelectProps) => (
  <Form.Select
    {...props}
    onChange={(event) => onChange(parseInt(event.target.value))}
    defaultValue={carSelectorDefaultValue}
  >
    <option value={carSelectorDefaultValue} disabled>
      {defaultOption}
    </option>
    {cars.map((car) => (
      <option key={car.carId} value={car.carId}>
        {car.infoCar}
      </option>
    ))}
  </Form.Select>
);

export default CarSelector;
