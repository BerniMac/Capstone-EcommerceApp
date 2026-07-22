/* ReviewFactory.java
   ReviewFactory class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Review;

public class ReviewFactory {

    public static Review createReview(String reviewId, String customerId, String productId, int rating, String comment, String reviewDate) {
        if (reviewId == null || reviewId.isEmpty() || customerId == null || customerId.isEmpty() || productId == null || productId.isEmpty() || rating < 1 || rating > 5) {
            return null;
        }

        return new Review.Builder()
                .setReviewId(reviewId)
                .setCustomerId(customerId)
                .setProductId(productId)
                .setRating(rating)
                .setComment(comment)
                .setReviewDate(reviewDate)
                .build();
    }
}
