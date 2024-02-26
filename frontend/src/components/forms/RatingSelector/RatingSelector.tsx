import { ChangeEventHandler } from "react";
import { Form, FormSelectProps } from "react-bootstrap";

const ratingOptions = [1, 2, 3, 4, 5];

interface RatingSelectorProps {
  rating: number;
  onChange: ChangeEventHandler<HTMLSelectElement>;
}

const RatingSelector = ({
  rating,
  onChange,
  ...props
}: RatingSelectorProps & FormSelectProps) => (
  <Form.Select {...props} onChange={onChange} value={rating} size="sm">
    {ratingOptions.map((option) => (
      <option key={option} value={option}>
        {Array(option).fill("★").join("")}
      </option>
    ))}
  </Form.Select>
);

export default RatingSelector;
