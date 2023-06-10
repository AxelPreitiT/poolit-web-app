package ar.edu.itba.paw.models.reviews;

import java.util.Arrays;
import java.util.List;

public enum CarReviewOptions {
    VERY_BAD_STATE(Arrays.asList(1)),
    VERY_DIRTY(Arrays.asList(1)),
    REDUCED_TRUNK_SPACE(Arrays.asList(1, 2)),
    NO_SEATBELTS(Arrays.asList(1)),
    NO_AIR_CONDITIONING(Arrays.asList(1)),
    UNCOMFORTABLE_SEATS(Arrays.asList(1, 2)),
    BAD_STATE(Arrays.asList(2)),
    DIRTY(Arrays.asList(2)),
    AIR_CONDITIONING_WORKING_BADLY(Arrays.asList(2)),
    SEATBELTS_MISSING(Arrays.asList(2)),
    GOOD_STATE(Arrays.asList(3)),
    CLEAN(Arrays.asList(3)),
    GOOD_TRUNK_SPACE(Arrays.asList(3, 4)),
    COMFORTABLE_SEATS(Arrays.asList(3, 4)),
    AIR_CONDITIONING_WORKING_WELL(Arrays.asList(3, 4)),
    SEATBELTS(Arrays.asList(3, 4, 5)),
    VERY_GOOD_STATE(Arrays.asList(4, 5)),
    VERY_CLEAN(Arrays.asList(4)),
    IMPECCABLE(Arrays.asList(5)),
    BIG_TRUNK_SPACE(Arrays.asList(5)),
    VERY_COMFORTABLE_SEATS(Arrays.asList(5)),
    AIR_CONDITIONING_WORKING_PERFECTLY(Arrays.asList(5));

    private final List<Integer> ratings;

    CarReviewOptions(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return this.name();
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public String getSpringMessageCode() {
        return "car.review.option." + this.name();
    }
}
