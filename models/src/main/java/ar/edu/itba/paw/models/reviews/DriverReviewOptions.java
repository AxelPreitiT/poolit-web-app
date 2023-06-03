package ar.edu.itba.paw.models.reviews;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DriverReviewOptions {
    NOT_FRIENDLY(Arrays.asList(1)),
    DANGEROUS_DRIVER(Arrays.asList(1)),
    UNPUNCTUAL(Arrays.asList(1, 2)),
    DISTRACTED_DRIVER(Arrays.asList(1)),
    NOT_RESPECTED_TRAFFIC_RULES(Arrays.asList(1, 2)),
    UNCOMFORTABLE_EXPERIENCE(Arrays.asList(2)),
    NOT_GOOD_TREATMENT(Arrays.asList(2)),
    IMPRUDENT_DRIVER(Arrays.asList(2)),
    RESPECTED_TRAFFIC_RULES(Arrays.asList(3, 4, 5)),
    ACCEPTABLE_DRIVER(Arrays.asList(3)),
    ACCEPTABLE_EXPERIENCE(Arrays.asList(3)),
    FRIENDLY(Arrays.asList(3)),
    PUNCTUAL(Arrays.asList(3, 4, 5)),
    VERY_FRIENDLY(Arrays.asList(4, 5)),
    SAFE_DRIVER(Arrays.asList(4)),
    GOOD_EXPERIENCE(Arrays.asList(4)),
    PROFESSIONAL_DRIVER(Arrays.asList(5)),
    EXCELLENT_EXPERIENCE(Arrays.asList(5));

    private final List<Integer> ratings;

    DriverReviewOptions(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return this.name();
    }

    public String getSpringMessageCode() {
        return "driver.review.option." + this.name();
    }

    public List<DriverReviewOptions> getOptionsByRating(int rating) {
        return Arrays.stream(DriverReviewOptions.values())
                .filter(option -> option.ratings.contains(rating))
                .collect(Collectors.toList());
    }

}
