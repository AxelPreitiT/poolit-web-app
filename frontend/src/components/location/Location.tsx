import React, { useEffect, useState } from "react";
import "./MovingCar.css";
import { FaCarSide } from "react-icons/fa";

interface CarProps {
  position: number;
}

const Car: React.FC<CarProps> = ({ position }) => {
  return (
    <div className="car" style={{ left: `${position * 600}px` }}>
      <FaCarSide size={40} color="black" />
    </div>
  );
};

const MovingCar: React.FC = () => {
  return (
    <div className="car-container">
      <Car position={0} />
    </div>
  );
};

export default MovingCar;
