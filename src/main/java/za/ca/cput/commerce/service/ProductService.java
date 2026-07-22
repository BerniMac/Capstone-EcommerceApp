package za.ca.cput.commerce.service;

/*
Author: Plamedie 230082629
19/07/2026
 */

import za.ca.cput.commerce.domain.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    List<Product> findAll();
    Product findById(String id);
    Product update(String id, Product product);
    void deleteById(String id);
}
