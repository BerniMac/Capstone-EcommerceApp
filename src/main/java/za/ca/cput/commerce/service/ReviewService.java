package za.ca.cput.commerce.service;

/*
Author: isheanesu chowuraya (223182192)
2026/07/19
 */
import za.ca.cput.commerce.domain.Review;

import java.util.List;

public interface ReviewService {
    Review save(Review review);
    List<Review> findAll();
    Review findById(String id);
    Review update(String id, Review review);
    void deleteById(String id);
}
