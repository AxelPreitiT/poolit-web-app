package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.CarServiceImpl;
import ar.edu.itba.paw.services.ReviewServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class ReviewServiceImplTest {

    private static final int rating = 3;
    private static final long tripId = 1L;
    private static final String review = "REVIEW";
    private static final Passenger passenger = new Passenger(1, "USER", "SURNAME", "EMAIL", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), Locale.US,"USER", 1L, LocalDateTime.now(), LocalDateTime.now());
    private static final long reviewId = 2L;

    @Mock
    private ReviewDao reviewDao;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testCreateReviews() {
        // precondiciones
        when(reviewDao.create(eq(tripId), any(), eq(rating), eq(review)))
                .thenReturn(new Review(reviewId, tripId, passenger, rating, review));

        // ejercitar la clase
        Review newReview = reviewService.createReview(tripId, passenger, rating, review);

        // assertions
        Assert.assertNotNull(newReview);
        Assert.assertEquals(reviewId, newReview.getReviewId());
        Assert.assertEquals(tripId, newReview.getTripId());
        Assert.assertEquals(passenger, newReview.getUser());
        Assert.assertEquals(rating, newReview.getRating());
        Assert.assertEquals(review, newReview.getReview());
    }
}
