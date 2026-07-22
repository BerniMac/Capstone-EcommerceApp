package service;

/*
Author: Plamedie 230082629
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = new Product.Builder()
                .setProductId("prod-1")
                .setProductName("Wireless Mouse")
                .setDescription("Ergonomic wireless mouse")
                .setCurrentPrice(299.99)
                .build();
    }

    @Test
    void whenSave_thenReturnSavedProduct() {
        given(productRepository.save(existingProduct)).willReturn(existingProduct);

        Product saved = productService.save(existingProduct);

        assertThat(saved).isEqualTo(existingProduct);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void whenFindAll_thenReturnListOfProducts() {
        given(productRepository.findAll()).willReturn(List.of(existingProduct));

        List<Product> products = productService.findAll();

        assertThat(products).hasSize(1).contains(existingProduct);
    }

    @Test
    void whenValidId_thenProductShouldBeFound() {
        given(productRepository.findById("prod-1")).willReturn(Optional.of(existingProduct));

        Product found = productService.findById("prod-1");

        assertThat(found.getProductId()).isEqualTo("prod-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(productRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.findById("bad-id"));
    }
//
//    @Test
//    void whenUpdate_thenRebuiltProductIsSavedWithNewFields() {
//        Product updateRequest = new Product.Builder()
//                .setProductName("Wireless Mouse Pro")
//                .setDescription("Ergonomic wireless mouse with USB-C")
//                .setCurrentPrice(349.99)
//                .build();
//
//        given(productRepository.findById("prod-1")).willReturn(Optional.of(existingProduct));
//        given(productRepository.save(any(Product.class)))
//                .willAnswer(invocation -> invocation.getArgument(0));
//
//        Product result = productService.update("prod-1", updateRequest);
//
//        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
//        verify(productRepository).save(captor.capture());
//        Product saved = captor.getValue();
//
//        assertThat(saved.getProductId()).isEqualTo("prod-1");
//        assertThat(saved.getProductName()).isEqualTo("Wireless Mouse Pro");
//        assertThat(saved.getDescription()).isEqualTo("Ergonomic wireless mouse with USB-C");
//        assertThat(saved.getCurrentPrice()).isEqualTo(349.99);
//        assertThat(result).isEqualTo(saved);
//    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(productRepository.findById("prod-1")).willReturn(Optional.of(existingProduct));

        productService.deleteById("prod-1");

        verify(productRepository, times(1)).delete(existingProduct);
    }
}
