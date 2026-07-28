package service;

/*
reviewServiceTest.java
author:isheanesu chowuraya 223182192
date 12 july 2026
 */
import domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ReviewRepository;
import service.impl.ReviewServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository repository;

    @InjectMocks
    private ReviewServiceImpl service;

    @Test
    void save() {

        Review review = new Review.Builder()
                .setRating(5)
                .setComment("Excellent")
                .build();

        when(repository.save(review)).thenReturn(review);

        Review saved = service.save(review);

        assertNotNull(saved);
        assertEquals(5, saved.getRating());

        verify(repository).save(review);
    }

    @Test
    void findById() {

        Review review = new Review.Builder()
                .setReviewId("1")
                .setRating(4)
                .setComment("Good")
                .build();

        when(repository.findById("1")).thenReturn(Optional.of(review));

        Optional<Review> result = service.findById("1");

        assertTrue(result.isPresent());
        assertEquals(4, result.get().getRating());

        verify(repository).findById("1");
    }

    @Test
    void findAll() {

        Review review = new Review.Builder()
                .setRating(5)
                .build();

        when(repository.findAll()).thenReturn(List.of(review));

        List<Review> reviews = service.findAll();

        assertEquals(1, reviews.size());

        verify(repository).findAll();
    }

    @Test
    void update() {

        Review existing = new Review.Builder()
                .setReviewId("1")
                .setRating(2)
                .setComment("Bad")
                .setReviewDate("2026-07-01")
                .build();

        Review updated = new Review.Builder()
                .setRating(5)
                .setComment("Excellent")
                .setReviewDate("2026-07-27")
                .build();

        when(repository.findById("1")).thenReturn(Optional.of(existing));

        Review expected = new Review.Builder()
                .copy(existing)
                .setRating(updated.getRating())
                .setComment(updated.getComment())
                .setReviewDate(updated.getReviewDate())
                .build();

        when(repository.save(any(Review.class))).thenReturn(expected);

        Review result = service.update("1", updated);

        assertEquals(5, result.getRating());
        assertEquals("Excellent", result.getComment());

        verify(repository).findById("1");
        verify(repository).save(any(Review.class));
    }

    @Test
    void delete() {

        doNothing().when(repository).deleteById("1");

        service.delete("1");

        verify(repository).deleteById("1");
    }
}
