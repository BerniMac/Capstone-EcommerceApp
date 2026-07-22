package za.ca.cput.commerce.repository;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
