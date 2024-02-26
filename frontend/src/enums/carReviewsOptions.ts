import CarReviewOptionModel from "@/models/CarReviewOptionModel";

enum CarReviewsOptions {
  VERY_BAD_STATE = "VERY_BAD_STATE",
  VERY_DIRTY = "VERY_DIRTY",
  REDUCED_TRUNK_SPACE = "REDUCED_TRUNK_SPACE",
  NO_SEATBELTS = "NO_SEATBELTS",
  NO_AIR_CONDITIONING = "NO_AIR_CONDITIONING",
  UNCOMFORTABLE_SEATS = "UNCOMFORTABLE_SEATS",
  BAD_STATE = "BAD_STATE",
  DIRTY = "DIRTY",
  AIR_CONDITIONING_WORKING_BADLY = "AIR_CONDITIONING_WORKING_BADLY",
  SEATBELTS_MISSING = "SEATBELTS_MISSING",
  GOOD_STATE = "GOOD_STATE",
  CLEAN = "CLEAN",
  GOOD_TRUNK_SPACE = "GOOD_TRUNK_SPACE",
  COMFORTABLE_SEATS = "COMFORTABLE_SEATS",
  AIR_CONDITIONING_WORKING_WELL = "AIR_CONDITIONING_WORKING_WELL",
  SEATBELTS = "SEATBELTS",
  VERY_GOOD_STATE = "VERY_GOOD_STATE",
  VERY_CLEAN = "VERY_CLEAN",
  IMPECCABLE = "IMPECCABLE",
  BIG_TRUNK_SPACE = "BIG_TRUNK_SPACE",
  VERY_COMFORTABLE_SEATS = "VERY_COMFORTABLE_SEATS",
  AIR_CONDITIONING_WORKING_PERFECTLY = "AIR_CONDITIONING_WORKING_PERFECTLY",
}

const carReviewOptionsByRating: Record<number, CarReviewsOptions[]> = {
  1: [
    CarReviewsOptions.VERY_BAD_STATE,
    CarReviewsOptions.VERY_DIRTY,
    CarReviewsOptions.REDUCED_TRUNK_SPACE,
    CarReviewsOptions.NO_SEATBELTS,
    CarReviewsOptions.NO_AIR_CONDITIONING,
    CarReviewsOptions.UNCOMFORTABLE_SEATS,
  ],
  2: [
    CarReviewsOptions.REDUCED_TRUNK_SPACE,
    CarReviewsOptions.UNCOMFORTABLE_SEATS,
    CarReviewsOptions.BAD_STATE,
    CarReviewsOptions.DIRTY,
    CarReviewsOptions.AIR_CONDITIONING_WORKING_BADLY,
    CarReviewsOptions.SEATBELTS_MISSING,
  ],
  3: [
    CarReviewsOptions.GOOD_STATE,
    CarReviewsOptions.CLEAN,
    CarReviewsOptions.GOOD_TRUNK_SPACE,
    CarReviewsOptions.COMFORTABLE_SEATS,
    CarReviewsOptions.AIR_CONDITIONING_WORKING_WELL,
    CarReviewsOptions.SEATBELTS,
  ],
  4: [
    CarReviewsOptions.GOOD_TRUNK_SPACE,
    CarReviewsOptions.COMFORTABLE_SEATS,
    CarReviewsOptions.AIR_CONDITIONING_WORKING_WELL,
    CarReviewsOptions.SEATBELTS,
    CarReviewsOptions.VERY_GOOD_STATE,
    CarReviewsOptions.VERY_CLEAN,
  ],
  5: [
    CarReviewsOptions.SEATBELTS,
    CarReviewsOptions.VERY_GOOD_STATE,
    CarReviewsOptions.IMPECCABLE,
    CarReviewsOptions.BIG_TRUNK_SPACE,
    CarReviewsOptions.VERY_COMFORTABLE_SEATS,
    CarReviewsOptions.AIR_CONDITIONING_WORKING_PERFECTLY,
  ],
};

export const getCarReviewOptionsByRating = (
  rating: number
): CarReviewOptionModel[] => {
  const options = carReviewOptionsByRating[rating] || [];
  return options.map((option) => ({ id: option }));
};

export default CarReviewsOptions;
