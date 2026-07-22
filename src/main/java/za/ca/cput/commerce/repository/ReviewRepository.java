package za.ca.cput.commerce.repository;

/*
Author: isheanesu chowuraya (223182192)
19/07/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
}