import DriverReviewsOptionModel from "@/models/DriverReviewsOptionModel";

enum DriverReviewsOptions {
  NOT_FRIENDLY = "NOT_FRIENDLY",
  DANGEROUS_DRIVER = "DANGEROUS_DRIVER",
  UNPUNCTUAL = "UNPUNCTUAL",
  DISTRACTED_DRIVER = "DISTRACTED_DRIVER",
  NOT_RESPECTED_TRAFFIC_RULES = "NOT_RESPECTED_TRAFFIC_RULES",
  UNCOMFORTABLE_EXPERIENCE = "UNCOMFORTABLE_EXPERIENCE",
  NOT_GOOD_TREATMENT = "NOT_GOOD_TREATMENT",
  IMPRUDENT_DRIVER = "IMPRUDENT_DRIVER",
  RESPECTED_TRAFFIC_RULES = "RESPECTED_TRAFFIC_RULES",
  ACCEPTABLE_DRIVER = "ACCEPTABLE_DRIVER",
  ACCEPTABLE_EXPERIENCE = "ACCEPTABLE_EXPERIENCE",
  FRIENDLY = "FRIENDLY",
  PUNCTUAL = "PUNCTUAL",
  VERY_FRIENDLY = "VERY_FRIENDLY",
  SAFE_DRIVER = "SAFE_DRIVER",
  GOOD_EXPERIENCE = "GOOD_EXPERIENCE",
  PROFESSIONAL_DRIVER = "PROFESSIONAL_DRIVER",
  EXCELLENT_EXPERIENCE = "EXCELLENT_EXPERIENCE",
}

const driverReviewOptionsByRating: Record<number, DriverReviewsOptions[]> = {
  1: [
    DriverReviewsOptions.NOT_FRIENDLY,
    DriverReviewsOptions.DANGEROUS_DRIVER,
    DriverReviewsOptions.UNPUNCTUAL,
    DriverReviewsOptions.DISTRACTED_DRIVER,
    DriverReviewsOptions.NOT_RESPECTED_TRAFFIC_RULES,
  ],
  2: [
    DriverReviewsOptions.UNPUNCTUAL,
    DriverReviewsOptions.NOT_RESPECTED_TRAFFIC_RULES,
    DriverReviewsOptions.UNCOMFORTABLE_EXPERIENCE,
    DriverReviewsOptions.NOT_GOOD_TREATMENT,
    DriverReviewsOptions.IMPRUDENT_DRIVER,
  ],
  3: [
    DriverReviewsOptions.RESPECTED_TRAFFIC_RULES,
    DriverReviewsOptions.ACCEPTABLE_DRIVER,
    DriverReviewsOptions.ACCEPTABLE_EXPERIENCE,
    DriverReviewsOptions.FRIENDLY,
    DriverReviewsOptions.PUNCTUAL,
  ],
  4: [
    DriverReviewsOptions.RESPECTED_TRAFFIC_RULES,
    DriverReviewsOptions.PUNCTUAL,
    DriverReviewsOptions.VERY_FRIENDLY,
    DriverReviewsOptions.SAFE_DRIVER,
    DriverReviewsOptions.GOOD_EXPERIENCE,
  ],
  5: [
    DriverReviewsOptions.RESPECTED_TRAFFIC_RULES,
    DriverReviewsOptions.PUNCTUAL,
    DriverReviewsOptions.VERY_FRIENDLY,
    DriverReviewsOptions.PROFESSIONAL_DRIVER,
    DriverReviewsOptions.EXCELLENT_EXPERIENCE,
  ],
};

export const getDriverReviewOptionsByRating = (
  rating: number
): DriverReviewsOptionModel[] => {
  const options = driverReviewOptionsByRating[rating] || [];
  return options.map((option) => {
    return {
      id: option,
    };
  });
};

export default DriverReviewsOptions;
