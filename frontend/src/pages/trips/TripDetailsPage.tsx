import { useParams } from "react-router-dom";

const TripDetailsPage = () => {
  const { tripId } = useParams<{ tripId: string }>();
  return <div>Trip {tripId}</div>;
};

export default TripDetailsPage;
