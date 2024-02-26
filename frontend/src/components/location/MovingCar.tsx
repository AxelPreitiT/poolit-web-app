import "./MovingCar.css";
import { FaCarSide } from "react-icons/fa";

const Car = () => {
  return (
    <div className="car">
      <FaCarSide size={40} color="#ffa216" />
    </div>
  );
};

const MovingCar: React.FC = () => {
  return (
    <div className="car-container">
      <Car />
    </div>
  );
};

export default MovingCar;
