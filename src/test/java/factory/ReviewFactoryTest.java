/* ReviewFactoryTest.java
   ReviewFactoryTest class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Review;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.ReviewFactory;

import static org.junit.jupiter.api.Assertions.*;

class ReviewFactoryTest {

    @Test
    void testCreateReviewSuccess() {
        Review review = ReviewFactory.createReview(
                "REV-77",
                "CUST-001",
                "PROD-002",
                5,
                "Great product, highly recommend!",
                "2026-06-21"
        );

        assertNotNull(review);
        assertEquals("REV-77", review.getReviewId());
        assertEquals("CUST-001", review.getCustomerId());
        assertEquals("PROD-002", review.getProductId());
        assertEquals(5, review.getRating());
        assertEquals("Great product, highly recommend!", review.getComment());
        assertEquals("2026-06-21", review.getReviewDate());
    }

    @Test
    void testCreateReviewFail() {
        Review review = ReviewFactory.createReview(
                "REV-77",
                "CUST-001",
                "PROD-002",
                6, // invalid rating (max 5)
                "Great product, highly recommend!",
                "2026-06-21"
        );
        assertNull(review);
    }
}
