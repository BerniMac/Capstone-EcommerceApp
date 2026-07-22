package controller;
/*

 * Author: Plamedie 230082629
 * Date: 19 july 2026
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
import za.ca.cput.commerce.controller.ProductController;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    private Product sampleProduct() {
        return new Product.Builder()
                .setProductId("prod-1")
                .setProductName("Wireless Mouse")
                .setDescription("Ergonomic wireless mouse")
                .setCurrentPrice(299.99)
                .build();
    }

    @Test
    void givenProducts_whenGetAllProducts_thenReturnJsonArray() throws Exception {
        given(productService.findAll()).willReturn(List.of(sampleProduct()));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].productId", org.hamcrest.Matchers.is("prod-1")));
    }

    @Test
    void givenValidId_whenGetProductById_thenReturnProduct() throws Exception {
        given(productService.findById("prod-1")).willReturn(sampleProduct());

        mockMvc.perform(get("/api/products/{id}", "prod-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", org.hamcrest.Matchers.is("Wireless Mouse")));
    }

    @Test
    void givenInvalidId_whenGetProductById_thenReturn404() throws Exception {
        given(productService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Product not found with id: bad-id"));

        mockMvc.perform(get("/api/products/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidProduct_whenCreateProduct_thenReturn201() throws Exception {
        Product product = sampleProduct();
        given(productService.save(any(Product.class))).willReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId", org.hamcrest.Matchers.is("prod-1")));
    }

    @Test
    void givenValidIdAndProduct_whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        Product updated = sampleProduct();
        given(productService.update(eq("prod-1"), any(Product.class))).willReturn(updated);

        mockMvc.perform(put("/api/products/{id}", "prod-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", org.hamcrest.Matchers.is("prod-1")));
    }

    @Test
    void givenValidId_whenDeleteProduct_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", "prod-1"))
                .andExpect(status().isNoContent());
    }
}
