package za.ca.cput.commerce.repository;

/*
Author: Plamedie 230082629
19/07/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
