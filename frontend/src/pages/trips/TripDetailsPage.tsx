import { useParams } from "react-router-dom";

const TripDetailsPage = () => {
  const { tripId } = useParams<{ tripId: string }>();
  return <h1>Trip {tripId}</h1>;
};

export default TripDetailsPage;
