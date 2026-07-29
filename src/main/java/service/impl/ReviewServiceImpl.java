package service.impl;
/*
reviewService.java
author:isheanesu chowuraya 223182192
date 12 july 2026
 */
import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReviewRepository;
import service.ReviewService;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review save(Review review) {
        return repository.save(review);
    }

    @Override
    public Review update(String reviewId, Review review) {

        Review existingReview = repository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Review updatedReview = new Review.Builder()
                .copy(existingReview)
                .setCustomerId(review.getCustomer())
                .setProductId(review.getProduct())
                .setRating(review.getRating())
                .setComment(review.getComment())
                .setReviewDate(review.getReviewDate())
                .build();

        return repository.save(updatedReview);
    }

    @Override
    public Optional<Review> findById(String reviewId) {
        return repository.findById(reviewId);
    }

    @Override
    public List<Review> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String reviewId) {
        repository.deleteById(reviewId);
    }
}