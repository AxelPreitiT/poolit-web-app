import PassengerReviewsOptionModel from "@/models/PassengerReviewsOptionModel";

enum PassengerReviewsOptions {
  DISRESPECTFUL = "DISRESPECTFUL",
  INAPPROPRIATE_BEHAVIOUR = "INAPPROPRIATE_BEHAVIOUR",
  UNPUNCTUAL = "UNPUNCTUAL",
  DIRTY = "DIRTY",
  NOT_FRIENDLY = "NOT_FRIENDLY",
  UNCOMMUNICATIVE = "UNCOMMUNICATIVE",
  BAD_BEHAVIOUR = "BAD_BEHAVIOUR",
  BAD_ATTITUDE = "BAD_ATTITUDE",
  ACCEPTABLE_BEHAVIOUR = "ACCEPTABLE_BEHAVIOUR",
  FRIENDLY = "FRIENDLY",
  PUNCTUAL = "PUNCTUAL",
  CLEAN = "CLEAN",
  ACCEPTABLE_ATTITUDE = "ACCEPTABLE_ATTITUDE",
  VERY_FRIENDLY = "VERY_FRIENDLY",
  CONSIDERATE = "CONSIDERATE",
  RESPONSIBLE = "RESPONSIBLE",
  GOOD_COMPANY = "GOOD_COMPANY",
}

const passengerReviewOptionsByRating: Record<
  number,
  PassengerReviewsOptions[]
> = {
  1: [
    PassengerReviewsOptions.DISRESPECTFUL,
    PassengerReviewsOptions.INAPPROPRIATE_BEHAVIOUR,
    PassengerReviewsOptions.UNPUNCTUAL,
    PassengerReviewsOptions.DIRTY,
    PassengerReviewsOptions.NOT_FRIENDLY,
  ],
  2: [
    PassengerReviewsOptions.UNPUNCTUAL,
    PassengerReviewsOptions.DIRTY,
    PassengerReviewsOptions.UNCOMMUNICATIVE,
    PassengerReviewsOptions.BAD_BEHAVIOUR,
    PassengerReviewsOptions.BAD_ATTITUDE,
  ],
  3: [
    PassengerReviewsOptions.ACCEPTABLE_BEHAVIOUR,
    PassengerReviewsOptions.FRIENDLY,
    PassengerReviewsOptions.PUNCTUAL,
    PassengerReviewsOptions.CLEAN,
    PassengerReviewsOptions.ACCEPTABLE_ATTITUDE,
  ],
  4: [
    PassengerReviewsOptions.PUNCTUAL,
    PassengerReviewsOptions.CLEAN,
    PassengerReviewsOptions.VERY_FRIENDLY,
    PassengerReviewsOptions.CONSIDERATE,
    PassengerReviewsOptions.RESPONSIBLE,
  ],
  5: [
    PassengerReviewsOptions.PUNCTUAL,
    PassengerReviewsOptions.CLEAN,
    PassengerReviewsOptions.VERY_FRIENDLY,
    PassengerReviewsOptions.CONSIDERATE,
    PassengerReviewsOptions.GOOD_COMPANY,
  ],
};

export const getPassengerReviewOptionsByRating = (
  rating: number
): PassengerReviewsOptionModel[] => {
  const options = passengerReviewOptionsByRating[rating] || [];
  return options.map((option) => ({ id: option }));
};

export default PassengerReviewsOptions;
