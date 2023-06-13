package ar.edu.itba.paw.models.reviews;

import java.util.Arrays;
import java.util.List;

public enum PassengerReviewOptions {
    DISRESPECTFUL(Arrays.asList(1)),
    INAPPROPRIATE_BEHAVIOUR(Arrays.asList(1)),
    UNPUNCTUAL(Arrays.asList(1, 2)),
    DIRTY(Arrays.asList(1, 2)),
    NOT_FRIENDLY(Arrays.asList(1)),
    UNCOMMUNICATIVE(Arrays.asList(2)),
    BAD_BEHAVIOUR(Arrays.asList(2)),
    BAD_ATTITUDE(Arrays.asList(2)),
    ACCEPTABLE_BEHAVIOUR(Arrays.asList(3)),
    FRIENDLY(Arrays.asList(3)),
    PUNCTUAL(Arrays.asList(3, 4, 5)),
    CLEAN(Arrays.asList(3, 4, 5)),
    ACCEPTABLE_ATTITUDE(Arrays.asList(3)),
    VERY_FRIENDLY(Arrays.asList(4, 5)),
    CONSIDERATE(Arrays.asList(4, 5)),
    RESPONSIBLE(Arrays.asList(4)),
    GOOD_COMPANY(Arrays.asList(5));

    private final List<Integer> ratings;

    PassengerReviewOptions(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return this.name();
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public String getSpringMessageCode() {
        return "passenger.review.option." + this.name();
    }
}
