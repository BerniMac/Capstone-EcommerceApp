/* ReviewFactory.java
   ReviewFactory class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026
*/
package factory;

import domain.Customer;
import domain.Product;
import domain.Review;

public class ReviewFactory {

    public static Review createReview(String reviewId, Customer customer, Product productId, int rating, String comment, String reviewDate) {
        if (reviewId == null || reviewId.isEmpty() || productId == null || rating < 1 || rating > 5) {
            return null;
        }

        return new Review.Builder()
                .setReviewId(reviewId)
                .setCustomerId(customer)
                .setProductId(productId)
                .setRating(rating)
                .setComment(comment)
                .setReviewDate(reviewDate)
                .build();
    }
}
