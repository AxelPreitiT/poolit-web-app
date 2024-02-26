import CarModel from "@/models/CarModel";
import { ChangeEventHandler } from "react";
import { Form, FormSelectProps } from "react-bootstrap";

export const carSelectorDefaultValue = -1;

interface CarSelectorProps {
  cars: CarModel[];
  defaultOption: string;
  onChange: ChangeEventHandler<HTMLSelectElement>;
  value: number;
}

const CarSelector = ({
  cars,
  defaultOption,
  onChange,
  value,
  ...props
}: CarSelectorProps & FormSelectProps) => (
  <Form.Select {...props} onChange={onChange} value={value}>
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
