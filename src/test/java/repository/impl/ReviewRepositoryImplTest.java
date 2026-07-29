/* ReviewRepositoryImplTest.java
   ReviewRepositoryImplTest class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026

package repository.impl;

import domain.Review;
import factory.ReviewFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReviewRepositoryImplTest {
    private final ReviewRepositoryImpl repo = new ReviewRepositoryImpl();

    @Test
    void testReviewCRUD() {
        // 1. Create
        Review review = ReviewFactory.createReview(
                "REV-77",
                "CUST-001",
                "PROD-002",
                5,
                "Great product, highly recommend!",
                "2026-06-21"
        );
        assertNotNull(review);
        Review created = repo.create(review);
        assertEquals(review.getReviewId(), created.getReviewId());

        // 2. Read
        Review read = repo.read(review.getReviewId());
        assertNotNull(read);
        assertEquals(5, read.getRating());

        // 3. Update
        Review updated = new Review.Builder().copy(read)
                .setComment("Absolutely amazing product!")
                .build();
        Review result = repo.update(updated);
        assertNotNull(result);
        assertEquals("Absolutely amazing product!", repo.read(review.getReviewId()).getComment());

        // 4. Find All
        assertEquals(1, repo.findAll().size());

        // 5. Delete
        boolean deleted = repo.delete(review.getReviewId());
        assertTrue(deleted);
        assertEquals(0, repo.findAll().size());
    }
}
*/