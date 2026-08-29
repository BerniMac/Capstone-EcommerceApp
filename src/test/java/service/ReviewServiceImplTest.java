package service;

/*
12/07/2026
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
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;
import za.ca.cput.commerce.repository.ReviewRepository;
import za.ca.cput.commerce.service.impl.ReviewServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository repository;

    @InjectMocks
    private ReviewServiceImpl service;

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
    void create() {

        when(repository.save(any(Review.class))).thenReturn(review);

        Review created = service.create(review);

        assertNotNull(created);
        assertEquals("R001", created.getReviewId());

        verify(repository).save(review);
    }

    @Test
    void read() {

        when(repository.findById("R001")).thenReturn(Optional.of(review));

        Optional<Review> found = service.read("R001");

        assertTrue(found.isPresent());
        assertEquals(review.getReviewId(), found.get().getReviewId());

        verify(repository).findById("R001");
    }

    @Test
    void update() {

        when(repository.existsById("R001")).thenReturn(true);
        when(repository.save(any(Review.class))).thenReturn(review);

        Review updated = service.update(review);

        assertNotNull(updated);
        assertEquals(5, updated.getRating());

        verify(repository).existsById("R001");
        verify(repository).save(review);
    }

    @Test
    void delete() {

        when(repository.existsById("R001")).thenReturn(true);

        boolean deleted = service.delete("R001");

        assertTrue(deleted);

        verify(repository).existsById("R001");
        verify(repository).deleteById("R001");
    }

    @Test
    void getAll() {

        when(repository.findAll()).thenReturn(List.of(review));

        List<Review> reviews = service.getAll();

        assertEquals(1, reviews.size());

        verify(repository).findAll();
    }

    @Test
    void getReviewsByProduct() {

        when(repository.findByProductProductId("P001"))
                .thenReturn(List.of(review));

        List<Review> reviews = service.getReviewsByProduct("P001");

        assertEquals(1, reviews.size());
        assertEquals("P001",
                reviews.get(0).getProduct().getProductId());

        verify(repository).findByProductProductId("P001");
    }

    @Test
    void getReviewsByCustomer() {

        when(repository.findByCustomerCustomerId("C001"))
                .thenReturn(List.of(review));

        List<Review> reviews = service.getReviewsByCustomer("C001");

        assertEquals(1, reviews.size());
        assertEquals("C001",
                reviews.get(0).getCustomer().getCustomerId());

        verify(repository).findByCustomerCustomerId("C001");
    }
}
