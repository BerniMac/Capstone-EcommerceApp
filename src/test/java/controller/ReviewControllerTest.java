package controller;
/*
reviewController.java
author:isheanesu chowuraya 223182192
date 19 july 2026
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Review;
import factory.ReviewFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import service.ReviewService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReviewController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {ReviewController.class, ReviewControllerTest.MockServiceConfig.class})
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockServiceConfig {

        @Bean
        ReviewService reviewService() {
            return Mockito.mock(ReviewService.class);
        }

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Test
    void createReview() throws Exception {

        Review review = new Review.Builder()
                .setRating(5)
                .setComment("Excellent")
                .setReviewDate("2026-07-27")
                .build();

        when(reviewService.save(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Excellent"));

        verify(reviewService).save(any(Review.class));
    }

    @Test
    void getReviewById() throws Exception {

        Review review = new Review.Builder()
                .setReviewId("1")
                .setRating(4)
                .setComment("Very good")
                .build();

        when(reviewService.findById("1")).thenReturn(Optional.of(review));

        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value("1"))
                .andExpect(jsonPath("$.rating").value(4));

        verify(reviewService).findById("1");
    }

    @Test
    void getAllReviews() throws Exception {

        Review review = new Review.Builder()
                .setReviewId("1")
                .setRating(5)
                .setComment("Excellent")
                .build();

        when(reviewService.findAll()).thenReturn(List.of(review));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(5));

        verify(reviewService).findAll();
    }

    @Test
    void updateReview() throws Exception {

        Review review = new Review.Builder()
                .setReviewId("1")
                .setRating(3)
                .setComment("Updated Review")
                .build();

        when(reviewService.update(eq("1"), any(Review.class))).thenReturn(review);

        mockMvc.perform(put("/reviews/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Updated Review"));

        verify(reviewService).update(eq("1"), any(Review.class));
    }

    @Test
    void deleteReview() throws Exception {

        doNothing().when(reviewService).delete("1");

        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isNoContent());

        verify(reviewService).delete("1");
    }
}
