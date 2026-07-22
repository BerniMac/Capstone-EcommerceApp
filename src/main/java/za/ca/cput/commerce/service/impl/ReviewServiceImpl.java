package za.ca.cput.commerce.service.impl;

/*
Author: isheanesu chowuraya (223182192)
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.repository.ReviewRepository;
import za.ca.cput.commerce.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review"+ id));
    }

    @Override
    public Review update(String id, Review review) {
        Review existing = findById(id);

        Review updated = new Review.Builder()
                .copy(existing)
                .setRating(review.getRating())
                .setComment(review.getComment())
                .build();
        return reviewRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        reviewRepository.delete(findById(id));
    }
}
