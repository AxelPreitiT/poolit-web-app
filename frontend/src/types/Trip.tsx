export type Trip = {
  tripId: number;
  originCity: {
    name: string;
  };
  originAddress: string;
  destinationCity: {
    name: string;
  };
  destinationAddress: string;
  dayOfWeekString: string;
  startDateString: string;
  endDateString: string;
  travelInfoDateString: string;
  startTimeString: string;
  integerQueryTotalPrice: string;
  decimalQueryTotalPrice: string;
  queryIsRecurrent: boolean;
  car: {
    imageId: string;
  };
};
