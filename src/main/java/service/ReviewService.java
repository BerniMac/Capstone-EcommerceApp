package service;
/*
reviewSerice.java
author:isheanesu chowuraya 223182192
date 12 july 2026
 */
import domain.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review save(Review review);

    Review update(String reviewId, Review review);

    Optional<Review> findById(String reviewId);

    List<Review> findAll();

    void delete(String reviewId);
}
