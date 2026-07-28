/* ReviewRepositoryImpl.java
   ReviewRepositoryImpl class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026

package repository.impl;

import domain.Review;
import repository.ReviewRepository;
import java.util.*;

public class ReviewRepositoryImpl implements ReviewRepository {
    private static ReviewRepositoryImpl repository = null;
    private final Map<String, Review> reviewDB = new HashMap<>();

    public ReviewRepositoryImpl() {
    }

    public static ReviewRepositoryImpl getRepository() {
        if (repository == null) {
            repository = new ReviewRepositoryImpl();
        }
        return repository;
    }

    @Override
    public Review create(Review review) {
        reviewDB.put(review.getReviewId(), review);
        return review;
    }

    @Override
    public Review read(String reviewId) {
        return reviewDB.get(reviewId);
    }

    @Override
    public Review update(Review review) {
        if (reviewDB.containsKey(review.getReviewId())) {
            reviewDB.put(review.getReviewId(), review);
            return review;
        }
        return null;
    }

    @Override
    public boolean delete(String reviewId) {
        return reviewDB.remove(reviewId) != null;
    }

    @Override
    public List<Review> findAll() {
        return new ArrayList<>(reviewDB.values());
    }
}
 */
