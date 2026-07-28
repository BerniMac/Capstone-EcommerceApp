package controller;
/*
import domain.Product;
import factory.ProductFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import service.ProductService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {ProductController.class, ProductControllerTest.MockServiceConfig.class})
public class ProductControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public ProductService productService() {
            return org.mockito.Mockito.mock(ProductService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Test
    public void testCreate() throws Exception {
        Product entity = ProductFactory.buildProduct("P001","Laptop","Gaming Laptop",999.99);
        when(productService.create(any(Product.class))).thenReturn(entity);
        String json = "{\"productId\":\"P001\",\"productName\":\"Laptop\",\"description\":\"Gaming Laptop\",\"currentPrice\":999.99}";
        mockMvc.perform(post("/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(entity.getProductId()));
    }

    @Test
    public void testRead() throws Exception {
        Product entity = ProductFactory.buildProduct("P001","Laptop","Gaming Laptop",999.99);
        when(productService.read(entity.getProductId())).thenReturn(entity);
        mockMvc.perform(get("/product/read/" + entity.getProductId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(entity.getProductId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Product entity = ProductFactory.buildProduct("P001","Laptop","Gaming Laptop",999.99);
        when(productService.update(any(Product.class))).thenReturn(entity);
        String json = "{\"productId\":\"P001\",\"productName\":\"Laptop\",\"description\":\"Gaming Laptop\",\"currentPrice\":999.99}";
        mockMvc.perform(post("/product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(entity.getProductId()));
    }

    @Test
    public void testDelete() throws Exception {
        Product entity = ProductFactory.buildProduct("P001","Laptop","Gaming Laptop",999.99);
        when(productService.delete(entity.getProductId())).thenReturn(true);
        mockMvc.perform(delete("/product/delete/" + entity.getProductId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Product entity = ProductFactory.buildProduct("P001","Laptop","Gaming Laptop",999.99);
        when(productService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/product/getall"))
                .andExpect(status().isOk());
    }
}

 */
