package za.ca.cput.commerce.service.impl;

/*
Author: Plamedie 230082629
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product"+ id));
    }

    @Override
    public Product update(String id, Product product) {
        Product existing = findById(id);

        Product updated = new Product.Builder()
                .copy(existing)
                .setProductName(product.getProductName())
                .setDescription(product.getDescription())
                .setCurrentPrice(product.getCurrentPrice())
                .build();
        return productRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        productRepository.delete(findById(id));
    }
}