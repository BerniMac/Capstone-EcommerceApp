package za.ac.cput.ecommerce.controller;
/*
   Author: isheanesu chowuraya (223182192)
   Date: 19 july 2026
*/
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.ReviewController;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.service.CustomerService;
import za.ca.cput.commerce.service.ProductService;
import za.ca.cput.commerce.service.ReviewService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Review review;

    @BeforeEach
    void setUp() {

        Customer customer = new Customer.Builder()
                .setCustomerId("C001")
                .build();

        Product product = new Product.Builder()
                .setProductId("P001")
                .build();

        review = new Review.Builder()
                .setReviewId("R001")
                .setCustomer(customer)
                .setProduct(product)
                .setRating(5)
                .setComment("Excellent product")
                .build();
    }

    @Test
    void create() throws Exception {

        when(service.create(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value("R001"));
    }

    @Test
    void read() throws Exception {

        when(service.read("R001")).thenReturn(Optional.of(review));

        mockMvc.perform(get("/api/reviews/R001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value("R001"));
    }

    @Test
    void getAll() throws Exception {

        when(service.getAll()).thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewId").value("R001"));
    }

    @Test
    void update() throws Exception {

        when(service.update(any(Review.class))).thenReturn(review);

        mockMvc.perform(put("/api/reviews/R001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value("R001"));
    }

    @Test
    void deleteReview() throws Exception {

        when(service.delete("R001")).thenReturn(true);

        mockMvc.perform(delete("/api/reviews/R001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getReviewsByProduct() throws Exception {

        when(service.getReviewsByProduct("P001"))
                .thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews/product/P001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product.productId").value("P001"));
    }

    @Test
    void getReviewsByCustomer() throws Exception {

        when(service.getReviewsByCustomer("C001"))
                .thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews/customer/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer.customerId").value("C001"));
    }
}
