package service;

/*
19/07/2026
Author: isheanesu chowuraya (223182192)
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.repository.ReviewRepository;
import za.ca.cput.commerce.service.impl.ReviewServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplUnitTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review existingReview;

    @BeforeEach
    void setUp() {
        existingReview = new Review.Builder()
                .setReviewId("rev-1")
                .setCustomerId("cust-1")
                .setProductId("prod-1")
                .setRating(4)
                .setComment("Good value for money")
                .setReviewDate("2026-01-01")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedReview() {
        given(reviewRepository.save(existingReview)).willReturn(existingReview);

        Review saved = reviewService.save(existingReview);

        assertThat(saved).isEqualTo(existingReview);
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    void whenFindAll_thenReturnListOfReviews() {
        given(reviewRepository.findAll()).willReturn(List.of(existingReview));

        List<Review> reviews = reviewService.findAll();

        assertThat(reviews).hasSize(1).contains(existingReview);
    }

    @Test
    void whenValidId_thenReviewShouldBeFound() {
        given(reviewRepository.findById("rev-1")).willReturn(Optional.of(existingReview));

        Review found = reviewService.findById("rev-1");

        assertThat(found.getReviewId()).isEqualTo("rev-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(reviewRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.findById("bad-id"));
    }

    @Test
    void whenUpdate_thenRebuiltReviewIsSavedWithNewFields() {
        Review updateRequest = new Review.Builder()
                .setRating(5)
                .setComment("Even better after a month of use")
                .build();

        given(reviewRepository.findById("rev-1")).willReturn(Optional.of(existingReview));
        given(reviewRepository.save(any(Review.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Review result = reviewService.update("rev-1", updateRequest);

        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(captor.capture());
        Review saved = captor.getValue();

        assertThat(saved.getReviewId()).isEqualTo("rev-1");
        assertThat(saved.getProductId()).isEqualTo("prod-1");
        assertThat(saved.getRating()).isEqualTo(5);
        assertThat(saved.getComment()).isEqualTo("Even better after a month of use");
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(reviewRepository.findById("rev-1")).willReturn(Optional.of(existingReview));

        reviewService.deleteById("rev-1");

        verify(reviewRepository, times(1)).delete(existingReview);
    }
}

