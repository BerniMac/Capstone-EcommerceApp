package controller;
/*
   Author: isheanesu chowuraya (223182192)
   Date: 19 july 2026
*/
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.ReviewController;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.service.ReviewService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReviewService reviewService;

    private Review sampleReview() {
        return new Review.Builder()
                .setReviewId("rev-1")
                .setCustomerId("cust-1")
                .setProductId("prod-1")
                .setRating(4)
                .setComment("Good value for money")
                .setReviewDate("2026-01-01")
                .build();
    }

    @Test
    void givenReviews_whenGetAllReviews_thenReturnJsonArray() throws Exception {
        given(reviewService.findAll()).willReturn(List.of(sampleReview()));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].reviewId", org.hamcrest.Matchers.is("rev-1")));
    }

    @Test
    void givenValidId_whenGetReviewById_thenReturnReview() throws Exception {
        given(reviewService.findById("rev-1")).willReturn(sampleReview());

        mockMvc.perform(get("/api/reviews/{id}", "rev-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", org.hamcrest.Matchers.is(4)));
    }

    @Test
    void givenInvalidId_whenGetReviewById_thenReturn404() throws Exception {
        given(reviewService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Review not found with id: bad-id"));

        mockMvc.perform(get("/api/reviews/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidReview_whenCreateReview_thenReturn201() throws Exception {
        Review review = sampleReview();
        given(reviewService.save(any(Review.class))).willReturn(review);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reviewId", org.hamcrest.Matchers.is("rev-1")));
    }

    @Test
    void givenValidIdAndReview_whenUpdateReview_thenReturnUpdatedReview() throws Exception {
        Review updated = sampleReview();
        given(reviewService.update(eq("rev-1"), any(Review.class))).willReturn(updated);

        mockMvc.perform(put("/api/reviews/{id}", "rev-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId", org.hamcrest.Matchers.is("rev-1")));
    }

    @Test
    void givenValidId_whenDeleteReview_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/reviews/{id}", "rev-1"))
                .andExpect(status().isNoContent());
    }
}
