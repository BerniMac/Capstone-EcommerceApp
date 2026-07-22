package za.ca.cput.commerce.repository;

/*
Author: 222709006 Qhama dyushu
19/07/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
