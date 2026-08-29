package service;

/*
Author: Plamedie 230082629
12/07/2026
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.factory.ProductFactory;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.impl.ProductServiceImpl;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;

    @BeforeEach
    void setUp() {

        product = ProductFactory.createProduct(
                "Gaming Laptop",
                "RTX 5090 Laptop",
                25999.99
        );

    }

    @Test
    void create() {

        when(repository.existsByProductNameIgnoreCase(product.getProductName()))
                .thenReturn(false);

        when(repository.save(product))
                .thenReturn(product);

        Product created = service.create(product);

        assertNotNull(created);
        assertEquals(product.getProductName(),
                created.getProductName());

        verify(repository).save(product);

    }

    @Test
    void getById() {

        when(repository.findById("P001"))
                .thenReturn(Optional.of(product));

        Product found = service.getById("P001");

        assertNotNull(found);

        verify(repository).findById("P001");

    }

    @Test
    void getAll() {

        when(repository.findAll())
                .thenReturn(List.of(product));

        List<Product> list = service.getAll();

        assertEquals(1, list.size());

        verify(repository).findAll();

    }

    @Test
    void searchByName() {

        when(repository.findByProductNameContainingIgnoreCase("Laptop"))
                .thenReturn(List.of(product));

        List<Product> results =
                service.searchByName("Laptop");

        assertFalse(results.isEmpty());

        verify(repository)
                .findByProductNameContainingIgnoreCase("Laptop");

    }

    @Test
    void update() {

        Product updated =
                new Product.Builder()
                        .copy(product)
                        .setProductName("Gaming Laptop Pro")
                        .build();

        when(repository.findById("P001"))
                .thenReturn(Optional.of(product));

        when(repository.save(any(Product.class)))
                .thenReturn(updated);

        Product result =
                service.update("P001", updated);

        assertEquals("Gaming Laptop Pro",
                result.getProductName());

        verify(repository).save(any(Product.class));

    }

    @Test
    void delete() {

        when(repository.findById("P001"))
                .thenReturn(Optional.of(product));

        service.delete("P001");

        verify(repository).delete(product);

    }

}