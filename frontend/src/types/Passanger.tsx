export type Passanger = {
  userId: number;
  userImageId: number;
  name: string;
  surname: string;
  recurrent: boolean;
  startDateString: string;
  endDateString: string;
  user: {
    passengerRating: number;
  };
  tripStarted: boolean;
};
