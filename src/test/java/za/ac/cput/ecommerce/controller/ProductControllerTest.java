package za.ac.cput.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.ProductController;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.factory.ProductFactory;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private ProductService service;

    @Test
    void createProduct() throws Exception {

        Product product =
                ProductFactory.createProduct(
                        "Gaming Laptop",
                        "RTX 5090",
                        25999.99);

        when(service.create(any(Product.class)))
                .thenReturn(product);

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName")
                        .value("Gaming Laptop"));

    }

    @Test
    void getProductById() throws Exception {

        Product product =
                ProductFactory.createProduct(
                        "Gaming Laptop",
                        "RTX 5090",
                        25999.99);

        when(service.getById("P001"))
                .thenReturn(product);

        mvc.perform(get("/api/products/P001"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName")
                        .value("Gaming Laptop"));

    }

    @Test
    void getAllProducts() throws Exception {

        Product product =
                ProductFactory.createProduct(
                        "Gaming Laptop",
                        "RTX 5090",
                        25999.99);

        when(service.getAll())
                .thenReturn(List.of(product));

        mvc.perform(get("/api/products"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(1));

    }

    @Test
    void searchProducts() throws Exception {

        Product product =
                ProductFactory.createProduct(
                        "Gaming Laptop",
                        "RTX 5090",
                        25999.99);

        when(service.searchByName("Laptop"))
                .thenReturn(List.of(product));

        mvc.perform(get("/api/products/search")
                        .param("productName","Laptop"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName")
                        .value("Gaming Laptop"));

    }

    @Test
    void updateProduct() throws Exception {

        Product updated =
                ProductFactory.createProduct(
                        "Gaming Laptop Pro",
                        "RTX 5090",
                        28999.99);

        when(service.update(any(), any()))
                .thenReturn(updated);

        mvc.perform(put("/api/products/P001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updated)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName")
                        .value("Gaming Laptop Pro"));

    }

    @Test
    void deleteProduct() throws Exception {

        mvc.perform(delete("/api/products/P001"))

                .andExpect(status().isNoContent());

    }

}
